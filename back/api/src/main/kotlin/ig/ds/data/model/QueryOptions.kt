package ig.ds.data.model

data class QueryOptions(
    var objectId: List<String>? = null,
    var regionId: List<String>? = null,
    val includeDeletedAttachments: Boolean? = null,
    val includeDeletedSignatures: Boolean? = null,
    var offset: Long? = null,
    var limit: Long? = null,
)