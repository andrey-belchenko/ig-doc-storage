import ig.ds.data.repository.AttachmentRepository
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
@QuarkusTest
class AttachmentRepositoryTest {

    @Inject
    lateinit var attachmentRepository: AttachmentRepository

    @Test
    fun createAttachment() {
        val filename = "test-document.pdf"

        val createdAttachment = attachmentRepository.create(filename)

        assertThat(createdAttachment).isNotNull
        assertThat(createdAttachment.id).isGreaterThan(0)
        assertThat(createdAttachment.filename).isEqualTo(filename)
    }
}