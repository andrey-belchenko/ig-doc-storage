package ig.ds.data.model

import java.time.OffsetDateTime


data class Attachment(
    var attachmentId: String?=null,
    var objectId: String,
    var regionId: String,
    var createdAt: OffsetDateTime? = null,
    var deletedAt: OffsetDateTime? = null,
    var createdBy: String? = null,
    var deletedBy: String? = null,
    var file: File,
    var signatures: List<Signature>? = null,
    var properties: Map<String, Any?>? = null
)