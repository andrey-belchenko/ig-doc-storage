package ig.ds.data.model

import java.time.OffsetDateTime

data class Signature(
    var signatureId: String?=null,
    var attachmentId: String,
    var createdAt: OffsetDateTime? = null,
    var deletedAt: OffsetDateTime? = null,
    var createdBy: String? = null,
    var deletedBy: String? = null,
    var file: File,
)
