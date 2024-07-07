import org.jreleaser.model.Active

plugins {
    kotlin("multiplatform") version "2.0.0"
    `maven-publish`
    id("org.jreleaser") version "1.13.1"
}

val publishVersion: String? by project

group = "nl.ncaj"
version = publishVersion ?: "local"

repositories {
    mavenCentral()
}

kotlin {
    macosX64()
    macosArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
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

    repositories {
        maven {
            setUrl(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

jreleaser {
    signing {
        active.set(Active.ALWAYS)
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("Sonatype") {
                    active.set(Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository("target/staging-deploy")
                }
            }
        }
    }
}
