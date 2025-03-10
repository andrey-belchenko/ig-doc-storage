// Root build.gradle.kts
plugins {
    kotlin("jvm") version "2.0.21" apply false // Apply false to avoid applying it to the root project
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm") // Apply Kotlin plugin to all subprojects

    repositories {
        mavenCentral()
        mavenLocal()
    }

    // Configure Java and Kotlin for all subprojects
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            javaParameters = true
        }
    }
}