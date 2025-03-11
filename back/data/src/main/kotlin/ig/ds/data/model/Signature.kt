package ig.ds.data.model

import java.time.OffsetDateTime

data class Signature(
    val signatureId: String,
    val attachmentId: String,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val deletedAt: OffsetDateTime? = null,
    val createdBy: String? = null,
    val deletedBy: String? = null,
    val fileId: String
)
