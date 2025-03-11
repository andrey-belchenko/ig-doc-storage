package ig.ds.data.model

import java.time.OffsetDateTime

data class Attachment(
    val attachmentId: String,
    val objectId: String,
    val regionId: String,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val deletedAt: OffsetDateTime? = null,
    val createdBy: String? = null,
    val deletedBy: String? = null,
    val fileId: String
)