package ig.ds.data.model

data class Signature(
    val id: Int,
    val signerName: String,
    val signedAt: java.time.LocalDateTime,
    val attachmentId: Int
)
