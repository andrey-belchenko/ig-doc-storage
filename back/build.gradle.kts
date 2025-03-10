// Root build.gradle.kts
plugins {
    kotlin("jvm") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.21" apply false
    id("io.quarkus") apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "io.quarkus")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            javaParameters.set(true)
        }
    }
    // Add dependencies using the low-level API
    dependencies {
        add("implementation", enforcedPlatform("${property("quarkusPlatformGroupId")}:${property("quarkusPlatformArtifactId")}:${property("quarkusPlatformVersion")}"))
//        add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    // Configure allOpen
    configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
        annotations(
            "jakarta.ws.rs.Path",
            "jakarta.enterprise.context.ApplicationScoped",
            "jakarta.persistence.Entity",
            "io.quarkus.test.junit.QuarkusTest"
        )
    }
}