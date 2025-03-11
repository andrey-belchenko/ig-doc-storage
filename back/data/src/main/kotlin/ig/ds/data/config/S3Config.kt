package ig.ds.data.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "s3")
interface S3Config {
    fun url(): String
    fun user(): String
    fun password(): String
    fun bucket(): String
}