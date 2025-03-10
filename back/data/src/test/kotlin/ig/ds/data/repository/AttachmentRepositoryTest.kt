package ig.ds.data.repository

import ig.ds.data.model.Attachment
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

@QuarkusTest
class AttachmentRepositoryTest {

    @Inject
    lateinit var userRepository: AttachmentRepository

    @Test
    @Transactional
    fun `should persist and retrieve user`() {
        // Create and persist a new user
        val user = Attachment().apply {
            fileName = "Документ 1"
            size = 2224
            createdAt = ZonedDateTime.now()
        }
        userRepository.persist(Attachment().apply {
            fileName = "Документ 1"
            size = 2224
            createdAt = ZonedDateTime.now()
        },Attachment().apply {
            fileName = "Документ 2"
            size = 224
            createdAt = ZonedDateTime.now()
        })


//        // Verify user was persisted and has an ID
//        assertNotNull(user.id)
//
//        // Retrieve user by username
//        val retrievedUser = userRepository.findByUsername("testuser")
//
//        // Verify retrieved user
//        assertNotNull(retrievedUser)
//        assertEquals("testuser@example.com", retrievedUser?.email)
    }
}