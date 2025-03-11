import ig.ds.data.model.File
import ig.ds.data.service.FileService
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
@QuarkusTest
class AttachmentRepositoryTest {

    @Inject
    lateinit var fileService: FileService

    @Test
    fun createFile() {
        val file = File(fileId = "test1", fileName = "test1.pdf", fileSize = 1000)

        val createdAttachment = fileService.addFile(file)

//        assertThat(createdAttachment).isNotNull
//        assertThat(createdAttachment.id).isGreaterThan(0)
//        assertThat(createdAttachment.filename).isEqualTo(filename)
    }
}