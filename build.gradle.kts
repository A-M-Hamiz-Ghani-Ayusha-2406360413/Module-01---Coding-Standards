plugins {
    java
    jacoco
    pmd
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"
description = "eshop"

val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
    testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
}

tasks.register<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"
    filter { excludeTestsMatching("*FunctionalTest") }
}

tasks.register<Test>("functionalTest") {
    description = "Runs functional tests."
    group = "verification"
    filter { includeTestsMatching("*FunctionalTest") }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/test/html"))
    }
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude("**/EshopApplication.class")
            }
        })
    )
}

pmd {
    isConsoleOutput = true
    toolVersion = "7.0.0-rc4"
    rulesMinimumPriority.set(5)
    ruleSetFiles = files("${project.rootDir}/pmd-ruleset.xml")
    ruleSets = listOf()
    isIgnoreFailures = true
}

tasks.withType<Pmd>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
    }
}
