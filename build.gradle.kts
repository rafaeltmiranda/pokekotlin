plugins {
    java
    kotlin("jvm") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    jacoco
    `maven-publish`
}

group = "me.sargunvohra.lib"
version = "2.5.0"

repositories {
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.retrofit2:adapter-rxjava:2.9.0")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("reflect"))
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
}

java {
    withSourcesJar()
    withJavadocJar()
}

jacoco {
    toolVersion = "0.8.5"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = true
        html.isEnabled = true
    }
}

publishing {
    repositories {
        mavenLocal()
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/pokeapi/pokekotlin")
            credentials {
                username = project.findProperty("gpr.user") as String?
                    ?: System.getenv("GITHUB_USER")
                password = project.findProperty("gpr.key") as String?
                    ?: System.getenv("GITHUB_KEY")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.sargunvohra.lib"
            artifactId = "pokekotlin"
            version = "2.5.0"
            from(components["java"])
        }
    }
}
