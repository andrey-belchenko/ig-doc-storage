package ig.ds.data.model

data class QueryOptions(
    var objectId: List<String>?,
    var regionId: List<String>?,
    val includeDeletedAttachments :Boolean?,
    val includeDeletedSignatures :Boolean?,
    var offset: Long?,
    var limit:Long?,
)