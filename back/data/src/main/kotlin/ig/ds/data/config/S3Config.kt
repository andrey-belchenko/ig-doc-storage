package ig.ds.data.config

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperties


@ApplicationScoped
@ConfigProperties(prefix = "s3")
class S3Config {
    lateinit var url: String
    lateinit var user: String
    lateinit var password: String
    lateinit var bucket: String
}