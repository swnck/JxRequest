plugins {
    id("java-library")
    id("maven-publish")
}

group = "de.swnck.JxRequest"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.reflections:reflections:0.9.12")
    implementation("com.google.code.gson:gson:2.8.8")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            this.groupId = project.group.toString()
            this.artifactId = project.name
            this.version = project.version.toString()
        }
    }

    repositories {
        maven {
            name = "nexus"
            val isSnapshot = project.version.toString().endsWith("-SNAPSHOT")
            url = if (isSnapshot)
                uri("https://nexus.synclyn.com/repository/maven-snapshots/")
            else
                uri("https://nexus.synclyn.com/repository/maven-releases/")
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }
}