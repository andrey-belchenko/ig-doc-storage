package ig.ds.data.repository
import ig.ds.data.model.Attachment
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AttachmentRepository : PanacheRepository<Attachment> {
    fun findByName(name: String): List<Attachment> {
        return list("name", name)
    }
}