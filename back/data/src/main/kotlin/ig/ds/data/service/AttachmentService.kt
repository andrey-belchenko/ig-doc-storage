package ig.ds.data.service

import ig.ds.data.jooq.Tables.*
import ig.ds.data.model.Attachment
import ig.ds.data.model.File
import ig.ds.data.model.Signature
import ig.ds.data.model.User
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jooq.DSLContext
import java.io.InputStream
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*


@ApplicationScoped
class AttachmentService @Inject constructor(
    private val dsl: DSLContext,
    private val s3Service: S3Service
) {

    private fun addFile(ctx: DSLContext, fileInfo: File, fileContentStream: InputStream) {
        s3Service.addFile(fileInfo.fileId!!, fileContentStream, fileInfo.fileSize)
        dsl.insertInto(FILE)
            .set(FILE.FILE_ID, fileInfo.fileId)
            .set(FILE.FILE_NAME, fileInfo.fileName)
            .set(FILE.FILE_SIZE, fileInfo.fileSize)
            .execute()
    }


    fun addAttachment(user: User , attachment: Attachment, fileContentStream: InputStream) {
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
                .execute()
        }
    }

    fun addSignature(user: User ,signature: Signature, fileContentStream: InputStream) {
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
                .set(SIGNATURE.SIGNATURE_ID, signature.attachmentId)
                .set(ATTACHMENT.CREATED_AT, signature.createdAt)
                .set(ATTACHMENT.CREATED_BY, signature.createdBy)
                .set(ATTACHMENT.FILE_ID, signature.file.fileId)
                .execute()
        }
    }

    fun getAttachmentsByObjectId(user: User, objectId: String): List<Attachment> {
        val attachments = dsl.select(
            ATTACHMENT.ATTACHMENT_ID,
            ATTACHMENT.OBJECT_ID,
            ATTACHMENT.REGION_ID,
            ATTACHMENT.CREATED_AT,
            ATTACHMENT.DELETED_AT,
            ATTACHMENT.CREATED_BY,
            ATTACHMENT.DELETED_BY,
            FILE.FILE_ID,
            FILE.FILE_NAME,
            FILE.FILE_SIZE
        )
            .from(ATTACHMENT)
            .join(FILE).on(ATTACHMENT.FILE_ID.eq(FILE.FILE_ID))
            .where(ATTACHMENT.OBJECT_ID.eq(objectId))
            .fetch { record ->
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
                    signatures = mutableListOf()
                )
            }

        fetchAndAssignSignatures(attachments)

        return attachments
    }

    private fun fetchAndAssignSignatures(attachments: List<Attachment>) {
        val attachmentIds = attachments.mapNotNull { it.attachmentId }
        if (attachmentIds.isEmpty()) return

        val signatures = dsl.select(
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
            .fetch { record ->
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