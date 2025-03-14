import nu.studer.gradle.jooq.JooqEdition

plugins {
    id("nu.studer.jooq") version "8.2"
}

dependencies {
    implementation("io.quarkus:quarkus-kotlin")
//    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("org.jooq:jooq:3.18.7")
    implementation("org.jooq:jooq-kotlin:3.18.7")
    implementation("io.quarkiverse.jooq:quarkus-jooq:2.0.1")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("software.amazon.awssdk:s3:2.17.52")
    implementation("software.amazon.awssdk:auth:2.17.52")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
//    TODO не работает на новой версии
//    implementation("software.amazon.awssdk:s3:2.30.36")
//    implementation("software.amazon.awssdk:auth:2.30.36")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("org.assertj:assertj-core:3.24.2")
    //  TODO не работает на новой версии
    jooqGenerator("org.postgresql:postgresql:42.6.0")
}

jooq {
    version.set("3.18.7")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/platform"
                    user = "postgres"
                    password = "1"
                }
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "attachments"
                        outputSchema= "attachments"
                        isOutputSchemaToDefault = false
                    }
                    target.apply {
                        packageName = "ig.ds.data.jooq"
                        directory = "src/main/generated"

                    }
                }
            }
        }
    }
}









