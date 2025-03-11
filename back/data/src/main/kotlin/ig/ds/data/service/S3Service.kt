package ig.ds.data.service

import ig.ds.data.config.S3Config
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.io.InputStream
import java.net.URI

@ApplicationScoped
class S3Service @Inject constructor(private val s3Config: S3Config) {

//    private val s3Client: S3Client = S3Client.builder()
//        .endpointOverride(URI.create(s3Config.url()))
//        .credentialsProvider(
//            StaticCredentialsProvider.create(
//                AwsBasicCredentials.create(s3Config.password(), s3Config.user())
//            )
//        )
//        .region(Region.of("us-east-1"))
//        .build()

    private val s3Client: S3Client by lazy {
        val credentials = AwsBasicCredentials.create(s3Config.user(), s3Config.password())
        S3Client.builder()
            .region(Region.of("us-west-2"))
            .endpointOverride(URI(s3Config.url()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials)).build()


    }

    fun addFile(key: String, inputStream: InputStream, contentLength: Long) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(s3Config.bucket())
            .key(key)
            .build()
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, contentLength))
    }

    fun getFile(key: String): InputStream {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(s3Config.bucket())
            .key(key)
            .build()

        return s3Client.getObject(getObjectRequest)
    }
}