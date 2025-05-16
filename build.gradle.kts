plugins {
    id("java-library")
    id("maven-publish")

}

group = "io.github.swnck"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.reflections:reflections:0.9.12")
    implementation("com.google.code.gson:gson:2.13.1")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/swnck/JxRequest")
            credentials {
                username = "swnck"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}