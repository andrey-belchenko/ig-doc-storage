package ig.ds.data.repository


import ig.ds.data.jooq.Tables.ATTACHMENT
import ig.ds.data.jooq.Tables.SIGNATURE
import ig.ds.data.model.Attachment
import ig.ds.data.model.Signature
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jooq.DSLContext


@ApplicationScoped
class AttachmentRepository @Inject constructor(private val dsl: DSLContext) {

    fun findById(id: Int): Attachment? {
        val attachmentRecord = dsl.selectFrom(ATTACHMENT)
            .where(ATTACHMENT.ID.eq(id))
            .fetchOne() ?: return null

        val signatures = dsl.selectFrom(SIGNATURE)
            .where(SIGNATURE.ATTACHMENT_ID.eq(id))
            .fetch { record ->
                Signature(
                    id = record.id,
                    signerName = record.signerName,
                    signedAt = record.signedAt,
                    attachmentId = record.attachmentId
                )
            }

        return Attachment(
            id = attachmentRecord.id,
            filename = attachmentRecord.filename,
            createdAt = attachmentRecord.createdAt,
            signatures = signatures
        )
    }

    fun create(filename: String): Attachment {
        val record = dsl.insertInto(ATTACHMENT)
            .set(ATTACHMENT.FILENAME, filename)
            .returning()
            .fetchOne()!!

        return Attachment(
            id = record.id,
            filename = record.filename,
            createdAt = record.createdAt
        )
    }
}