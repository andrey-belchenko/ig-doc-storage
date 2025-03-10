import nu.studer.gradle.jooq.JooqEdition

plugins {
    id("nu.studer.jooq") version "8.2"
}

dependencies {
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("org.jooq:jooq:3.20.1")
    implementation("io.quarkiverse.jooq:quarkus-jooq:2.0.1")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("io.quarkus:quarkus-junit5")
    jooqGenerator("org.postgresql:postgresql:42.6.0")
}

jooq {
    version.set("3.20.1")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)
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
                    }
                    target.apply {
                        packageName = "ig.ds.data.model.generated"
                        directory = "src/main/kotlin/ig/ds/data/model/generated"
                    }
                }
            }
        }
    }
}









