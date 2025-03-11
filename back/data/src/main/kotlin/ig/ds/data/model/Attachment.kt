package ig.ds.data.model
data class Attachment(
    val id: Int,
    val filename: String,
    val createdAt: java.time.LocalDateTime,
    val signatures: List<Signature> = emptyList()
)