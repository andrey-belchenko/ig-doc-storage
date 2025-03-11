import ig.ds.data.model.Attachment
import ig.ds.data.model.File
import ig.ds.data.model.Signature
import ig.ds.data.model.User
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
            properties = mapOf("description" to "test file", "comments" to listOf("comment1","comment2"))
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
            attachmentId = "7c45df42-c123-488d-942e-85c411f3d7b0",
            file = fileInfo
        )
        val fileContentStream = ByteArrayInputStream(fileContentBytes)
        attachmentService.addSignature(user, signature, fileContentStream)
    }
    @Test
    fun getAttachments() {
        val user = User(userId = "tester")
        val attachments =   attachmentService.getAttachmentsByObjectId(user, "object1")
        val a =attachments
    }
}