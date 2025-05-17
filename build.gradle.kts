plugins {
    id("java-library")
    id("maven-publish")

}

group = "io.github.swnck"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    implementation("org.slf4j:slf4j-api:2.0.17")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
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