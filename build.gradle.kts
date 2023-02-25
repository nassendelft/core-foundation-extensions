plugins {
    kotlin("multiplatform") version "1.8.10"
    `maven-publish`
    signing
}

val publishVersion: String? by project

group = "nl.ncaj"
version = publishVersion ?: "local"

repositories {
    mavenCentral()
}

kotlin {
    macosX64()
    sourceSets {
        val macosX64Main by getting
        val macosX64Test by getting
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("ossrhUsername")
                password = System.getenv("ossrhPassword")
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

        pom {
            name.set("core-foundation-extensions")
            description.set("Kotlin macos helper extensions for Kotlin multiplatform")
            url.set("https://github.com/nassendelft/core-foundation-extensions")

            licenses {
                license {
                    name.set("GPL-3.0")
                    url.set("https://opensource.org/license/gpl-3-0/")
                }
            }
            developers {
                developer {
                    id.set("nassendelft")
                    name.set("Nick")
                    email.set("n.assendelft@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/nassendelft/core-foundation-extensions")
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("signing_keyId"),
        System.getenv("signing_key"),
        System.getenv("signing_password"),
    )
    sign(publishing.publications)
}
