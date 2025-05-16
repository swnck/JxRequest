plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "io.github.swnck"
version = "1.0.6"

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
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("JxRequest")
                description.set("Effortlessly streamline Http requests in Java, bypassing the complexities of standard HTTP interactions.")
                url.set("https://github.com/swnck/JxRequest")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("swnck")
                        name.set("Nick Schweizer")
                        email.set("datapack3t.contact@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/swnck/JxRequest.git")
                    developerConnection.set("scm:git:ssh://github.com:swnck/JxRequest.git")
                    url.set("https://github.com/swnck/JxRequest")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            
        }
    }
}