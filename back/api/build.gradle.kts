
dependencies {
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-swagger-ui")


    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("org.jooq:jooq:3.18.7")
    implementation("org.jooq:jooq-kotlin:3.18.7")
    implementation("io.quarkiverse.jooq:quarkus-jooq:2.0.1")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("software.amazon.awssdk:s3:2.17.52")
    implementation("software.amazon.awssdk:auth:2.17.52")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

    implementation(project(":data"))
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")

}


