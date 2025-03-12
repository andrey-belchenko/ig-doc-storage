import ig.ds.data.model.*
import ig.ds.data.service.AttachmentService
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

@QuarkusTest
class AttachmentServiceTest {

    @Inject
    lateinit var attachmentService: AttachmentService

    @Test
    fun generateData() {
        val user = User(userId = "tester")
        for (regNum in 1..2) {
            for (objectNum in 1..20) {
                for (num in 1..3) {
                    val attachmentId = createAttachment(user, "region$regNum", "object-$regNum-$objectNum", num)
                    if (num > 2) {
                        attachmentService.deleteAttachment(user, attachmentId)
                    }
                }
            }
        }
    }

    fun createAttachment(user: User, regionId: String, objectId: String, number: Int): String {
        val fileContentBytes = "Test content".toByteArray()
        val fileSize = fileContentBytes.size.toLong()
        val info = "$regionId-$objectId-$number"
        val fileName = "attachment $info.txt"
        val fileInfo = File(
            fileName = fileName,
            fileSize = fileSize
        )
        val attachment = Attachment(
            objectId = objectId,
            regionId = regionId,
            file = fileInfo,
            properties = mapOf("description" to "this id file $fileName", "comments" to listOf("comment1", "comment2"))
        )
        val fileContentStream = ByteArrayInputStream(fileContentBytes)
        val attachmentId = attachmentService.addAttachment(user, attachment, fileContentStream)

        for (i in 1..3) {
            val signatureId = createSignature(user, attachmentId, info, i)
            if (i > 2) {
                attachmentService.deleteSignature(user, signatureId)
            }
        }
        return attachmentId
    }

    fun createSignature(user: User, attachmentId: String, info: String, number: Int): String {
        val fileContentBytes = "Test content".toByteArray()
        val fileSize = fileContentBytes.size.toLong()
        val fileName = "signature $info-$number.txt"
        val fileInfo = File(
            fileName = fileName,
            fileSize = fileSize
        )
        val signature = Signature(
            attachmentId = attachmentId,
            file = fileInfo
        )
        val fileContentStream = ByteArrayInputStream(fileContentBytes)

        return attachmentService.addSignature(user, signature, fileContentStream)
    }


    @Test
    fun addAttachment() {
        val fileContentBytes = "Test content".toByteArray()
        val fileSize = fileContentBytes.size.toLong()
        val fileInfo = File(
            fileName = "test.txt",
            fileSize = fileSize
        )
        val user = User(userId = "tester")
        val attachment = Attachment(
            objectId = "object1",
            regionId = "region1",
            file = fileInfo,
            properties = mapOf("description" to "test file", "comments" to listOf("comment1", "comment2"))
        )
        val fileContentStream = ByteArrayInputStream(fileContentBytes)
        attachmentService.addAttachment(user, attachment, fileContentStream)
    }

    @Test
    fun addSignature() {
        val fileContentBytes = "Test content".toByteArray()
        val fileSize = fileContentBytes.size.toLong()
        val fileInfo = File(
            fileName = "test.txt",
            fileSize = fileSize
        )
        val user = User(userId = "tester")
        val signature = Signature(
            attachmentId = "61cd5833-c044-442e-a152-d2927247bc7c",
            file = fileInfo
        )
        val fileContentStream = ByteArrayInputStream(fileContentBytes)
        attachmentService.addSignature(user, signature, fileContentStream)
    }

    @Test
    fun getAttachments() {
        val user = User(userId = "tester")
        val attachments = attachmentService.getAttachments(user, QueryOptions(objectId = listOf("object10"), includeDeletedAttachments = true))
        val a = attachments
    }
}