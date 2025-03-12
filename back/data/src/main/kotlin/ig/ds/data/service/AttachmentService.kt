package ig.ds.data.service

import ig.ds.data.jooq.Tables.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jooq.DSLContext
import org.jooq.JSONB
import java.io.InputStream
import java.time.OffsetDateTime
import java.util.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ig.ds.data.jooq.enums.AccessLevel
import ig.ds.data.model.*


@ApplicationScoped
class AttachmentService @Inject constructor(
    private val dsl: DSLContext,
    private val s3Service: S3Service
) {

    fun getFileInfo(fileId: String): File {
        // TODO check access
        return dsl.selectFrom(FILE)
            .where(FILE.FILE_ID.eq(fileId))
            .fetch { record ->
                File(
                    fileId = record[FILE.FILE_ID],
                    fileName = record[FILE.FILE_NAME],
                    fileSize = record[FILE.FILE_SIZE]
                )

            }.firstOrNull() ?: throw IllegalArgumentException("File $fileId not found")
    }

    fun getFileContent(fileId: String):InputStream{
        // TODO check access
        return s3Service.getFile(fileId)
    }

    private fun addFile(ctx: DSLContext, fileInfo: File, fileContentStream: InputStream) {
        s3Service.addFile(fileInfo.fileId!!, fileContentStream, fileInfo.fileSize)
        dsl.insertInto(FILE)
            .set(FILE.FILE_ID, fileInfo.fileId)
            .set(FILE.FILE_NAME, fileInfo.fileName)
            .set(FILE.FILE_SIZE, fileInfo.fileSize)
            .execute()
    }

    private fun checkAccess(userId: String, regionId: String, accessLevel: AccessLevel) {
        if (dsl.selectFrom(PERMISSION)
                .where(PERMISSION.USER_ID.eq(userId))
                .and(PERMISSION.REGION_ID.eq(regionId))
                .and(PERMISSION.ACCESS_LEVEL.eq(accessLevel))
                .fetch()
                .isEmpty()
        )
            throw SecurityException("User does not have $accessLevel access to region $regionId")
    }


    fun addAttachment(user: User, attachment: Attachment, fileContentStream: InputStream): String {
        checkAccess(user.userId, attachment.regionId, AccessLevel.WRITE)
        if (attachment.attachmentId == null) {
            attachment.attachmentId = UUID.randomUUID().toString()
        }
        if (attachment.file.fileId == null) {
            attachment.file.fileId = attachment.attachmentId
        }
        if (attachment.createdAt == null) {
            attachment.createdAt = OffsetDateTime.now()
        }
        if (attachment.createdBy == null) {
            attachment.createdBy = user.userId
        }
        val objectMapper = jacksonObjectMapper()


        val propertiesJson = objectMapper.writeValueAsString(attachment.properties)
        dsl.transaction { config ->
            val ctx = config.dsl()
            addFile(ctx, attachment.file, fileContentStream)
            ctx.insertInto(ATTACHMENT)
                .set(ATTACHMENT.ATTACHMENT_ID, attachment.attachmentId)
                .set(ATTACHMENT.OBJECT_ID, attachment.objectId)
                .set(ATTACHMENT.REGION_ID, attachment.regionId)
                .set(ATTACHMENT.CREATED_AT, attachment.createdAt)
                .set(ATTACHMENT.CREATED_BY, attachment.createdBy)
                .set(ATTACHMENT.FILE_ID, attachment.file.fileId)
                .set(ATTACHMENT.PROPERTIES, JSONB.valueOf(propertiesJson))
                .execute()
        }
        return attachment.attachmentId!!
    }

    fun addSignature(user: User, signature: Signature, fileContentStream: InputStream): String {
        val attachment = dsl.selectFrom(ATTACHMENT)
            .where(ATTACHMENT.ATTACHMENT_ID.eq(signature.attachmentId))
            .fetchOne() ?: throw IllegalArgumentException("Attachment ${signature.attachmentId} not found")
        checkAccess(user.userId, attachment.regionId, AccessLevel.WRITE)

        if (signature.signatureId == null) {
            signature.signatureId = UUID.randomUUID().toString()
        }
        if (signature.createdAt == null) {
            signature.createdAt = OffsetDateTime.now()
        }
        if (signature.file.fileId == null) {
            signature.file.fileId = signature.signatureId
        }
        if (signature.createdBy == null) {
            signature.createdBy = user.userId
        }
        dsl.transaction { config ->
            val ctx = config.dsl()
            addFile(ctx, signature.file, fileContentStream)
            ctx.insertInto(SIGNATURE)
                .set(SIGNATURE.ATTACHMENT_ID, signature.attachmentId)
                .set(SIGNATURE.SIGNATURE_ID, signature.signatureId)
                .set(ATTACHMENT.CREATED_AT, signature.createdAt)
                .set(ATTACHMENT.CREATED_BY, signature.createdBy)
                .set(ATTACHMENT.FILE_ID, signature.file.fileId)
                .execute()
        }
        return signature.signatureId!!
    }

    fun deleteAttachment(user: User, attachmentId: String) {
        val attachment = dsl.selectFrom(ATTACHMENT)
            .where(ATTACHMENT.ATTACHMENT_ID.eq(attachmentId))
            .fetchOne() ?: throw IllegalArgumentException("Attachment $attachmentId not found")

        checkAccess(user.userId, attachment.regionId, AccessLevel.WRITE)

        dsl.transaction { config ->
            val ctx = config.dsl()
            ctx.update(ATTACHMENT)
                .set(ATTACHMENT.DELETED_AT, OffsetDateTime.now())
                .set(ATTACHMENT.DELETED_BY, user.userId)
                .where(ATTACHMENT.ATTACHMENT_ID.eq(attachmentId))
                .execute()

            ctx.update(SIGNATURE)
                .set(SIGNATURE.DELETED_AT, OffsetDateTime.now())
                .set(SIGNATURE.DELETED_BY, user.userId)
                .where(SIGNATURE.ATTACHMENT_ID.eq(attachmentId))
                .and(SIGNATURE.DELETED_AT.isNull()) // Only update signatures that are not already deleted
                .execute()
        }
    }

    fun deleteSignature(user: User, signatureId: String) {
        val signature = dsl.selectFrom(SIGNATURE)
            .where(SIGNATURE.SIGNATURE_ID.eq(signatureId))
            .fetchOne() ?: throw IllegalArgumentException("Signature $signatureId not found")

        val attachment = dsl.selectFrom(ATTACHMENT)
            .where(ATTACHMENT.ATTACHMENT_ID.eq(signature.attachmentId))
            .fetchOne() ?: throw IllegalArgumentException("Attachment ${signature.attachmentId} not found")

        checkAccess(user.userId, attachment.regionId, AccessLevel.WRITE)

        dsl.transaction { config ->
            val ctx = config.dsl()
            ctx.update(SIGNATURE)
                .set(SIGNATURE.DELETED_AT, OffsetDateTime.now())
                .set(SIGNATURE.DELETED_BY, user.userId)
                .where(SIGNATURE.SIGNATURE_ID.eq(signatureId))
                .execute()
        }
    }

    fun getAttachments(user: User, queryOptions: QueryOptions): List<Attachment> {
        val objectMapper = jacksonObjectMapper()
        val query = dsl.select(
            ATTACHMENT.ATTACHMENT_ID,
            ATTACHMENT.OBJECT_ID,
            ATTACHMENT.REGION_ID,
            ATTACHMENT.CREATED_AT,
            ATTACHMENT.DELETED_AT,
            ATTACHMENT.CREATED_BY,
            ATTACHMENT.DELETED_BY,
            ATTACHMENT.PROPERTIES,
            FILE.FILE_ID,
            FILE.FILE_NAME,
            FILE.FILE_SIZE
        )
            .from(ATTACHMENT)
            .join(FILE).on(ATTACHMENT.FILE_ID.eq(FILE.FILE_ID))
            .join(PERMISSION).on(ATTACHMENT.REGION_ID.eq(PERMISSION.REGION_ID))
            .and(PERMISSION.USER_ID.eq(user.userId))
            .and(PERMISSION.ACCESS_LEVEL.`in`(AccessLevel.READ, AccessLevel.WRITE))

        queryOptions.objectId?.let { objectIds ->
            query.and(ATTACHMENT.OBJECT_ID.`in`(objectIds))
        }

        queryOptions.regionId?.let { regionIds ->
            query.and(ATTACHMENT.REGION_ID.`in`(regionIds))
        }

        if (queryOptions.includeDeletedAttachments != true) {
            query.and(ATTACHMENT.DELETED_AT.isNull())
        }

        queryOptions.offset?.let { offset ->
            query.offset(offset)
        }

        queryOptions.limit?.let { limit ->
            query.limit(limit)
        }

        val attachments = query.fetch { record ->
            val propertiesJson = record[ATTACHMENT.PROPERTIES]
            val propertiesMap = propertiesJson?.let {
                objectMapper.readValue<Map<String, Any?>>(it.data())
            }
            Attachment(
                attachmentId = record[ATTACHMENT.ATTACHMENT_ID],
                objectId = record[ATTACHMENT.OBJECT_ID],
                regionId = record[ATTACHMENT.REGION_ID],
                createdAt = record[ATTACHMENT.CREATED_AT],
                deletedAt = record[ATTACHMENT.DELETED_AT],
                createdBy = record[ATTACHMENT.CREATED_BY],
                deletedBy = record[ATTACHMENT.DELETED_BY],
                file = File(
                    fileId = record[FILE.FILE_ID],
                    fileName = record[FILE.FILE_NAME],
                    fileSize = record[FILE.FILE_SIZE]
                ),
                signatures = mutableListOf(),
                properties = propertiesMap
            )
        }

        fetchAndAssignSignatures(attachments, queryOptions)

        return attachments
    }

    private fun fetchAndAssignSignatures(attachments: List<Attachment>, queryOptions: QueryOptions) {
        val attachmentIds = attachments.mapNotNull { it.attachmentId }
        if (attachmentIds.isEmpty()) return

        val signatureQuery = dsl.select(
            SIGNATURE.SIGNATURE_ID,
            SIGNATURE.ATTACHMENT_ID,
            SIGNATURE.CREATED_AT,
            SIGNATURE.DELETED_AT,
            SIGNATURE.CREATED_BY,
            SIGNATURE.DELETED_BY,
            FILE.FILE_ID,
            FILE.FILE_NAME,
            FILE.FILE_SIZE
        )
            .from(SIGNATURE)
            .join(FILE).on(SIGNATURE.FILE_ID.eq(FILE.FILE_ID))
            .where(SIGNATURE.ATTACHMENT_ID.`in`(attachmentIds))


        if (queryOptions.includeDeletedSignatures != true) {
            signatureQuery.and(SIGNATURE.DELETED_AT.isNull())
        }

        val signatures = signatureQuery.fetch { record ->
            Signature(
                signatureId = record[SIGNATURE.SIGNATURE_ID],
                attachmentId = record[SIGNATURE.ATTACHMENT_ID],
                createdAt = record[SIGNATURE.CREATED_AT],
                deletedAt = record[SIGNATURE.DELETED_AT],
                createdBy = record[SIGNATURE.CREATED_BY],
                deletedBy = record[SIGNATURE.DELETED_BY],
                file = File(
                    fileId = record[FILE.FILE_ID],
                    fileName = record[FILE.FILE_NAME],
                    fileSize = record[FILE.FILE_SIZE]
                )
            )
        }

        val signaturesByAttachmentId = signatures.groupBy { it.attachmentId }

        attachments.forEach { attachment ->
            attachment.signatures = signaturesByAttachmentId[attachment.attachmentId] ?: emptyList()
        }
    }


}