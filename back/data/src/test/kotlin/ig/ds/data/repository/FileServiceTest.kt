import ig.ds.data.model.File
import ig.ds.data.service.FileService
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

@QuarkusTest
class FileServiceTest {

    @Inject
    lateinit var fileService: FileService

    @Test
    fun addFile() {
        val fileContentBytes = "Test content".toByteArray()
        val fileSize = fileContentBytes.size.toLong()
        val fileInfo = File(
            fileId = "1245",
            fileName = "test.txt",
            fileSize = fileSize
        )
        val fileContentStream = ByteArrayInputStream(fileContentBytes)
        fileService.addFile(fileInfo, fileContentStream)
    }
}