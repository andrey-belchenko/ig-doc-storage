package ig.ds.data.model
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.ZonedDateTime


@Entity
@Table(name = "attachment")
class Attachment : PanacheEntity() {
    var fileName: String? = null
    var fileSize: Int? = null
    @Column(columnDefinition = "timestamptz")
    var createdAt: ZonedDateTime? = null
    @Column(columnDefinition = "timestamptz")
    var deletedAt: ZonedDateTime? = null
}