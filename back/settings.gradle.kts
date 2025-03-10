pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(extra["quarkusPluginId"].toString()) version extra["quarkusPluginVersion"].toString()
    }
}

rootProject.name = "back"

include("data")
include("api")
