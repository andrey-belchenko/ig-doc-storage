package ig.ds.data.service

import ig.ds.data.config.S3Config
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.net.URI
import java.nio.file.Paths

@ApplicationScoped
class S3Service @Inject constructor(private val s3Config: S3Config) {

    private val s3Client: S3Client = S3Client.builder()
        .endpointOverride(URI.create(s3Config.url()))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(s3Config.password(), s3Config.user())
            )
        )
        .region(Region.of("us-east-1"))
        .build()

    fun putFile(id: String, filePath: String) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(s3Config.bucket())
            .key(id)
            .build()

        s3Client.putObject(putObjectRequest, Paths.get(filePath))
    }

    fun getFile(id: String): ByteArray {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(s3Config.bucket())
            .key(id)
            .build()

        return s3Client.getObject(getObjectRequest).readAllBytes()
    }
}