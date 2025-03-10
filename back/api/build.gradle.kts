
dependencies {
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-swagger-ui")
    implementation(project(":data"))
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")

}


