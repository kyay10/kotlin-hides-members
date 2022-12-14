@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.github.gmazzo.buildconfig")
    id("convention.publication")
}

repositories {
    mavenCentral()
}
dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    kapt("com.google.auto.service:auto-service:1.0.1")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")

    // Needed for running tests since the tests inherit out classpath
    implementation(project(":prelude"))

    testImplementation(kotlin("test-junit5"))
    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.9")
}

buildConfig {
    packageName(group.toString().replace("-", ""))
    buildConfigField(
        "String",
        "KOTLIN_PLUGIN_ID",
        "\"${rootProject.extra["kotlin_plugin_id"].toString().replace("-", "")}\""
    )
    buildConfigField(
        "String",
        "SAMPLE_JVM_MAIN_PATH",
        "\"${rootProject.projectDir.absolutePath}/sample/src/jvmMain/\""
    )
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}

java {
    withSourcesJar()
    withJavadocJar()
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            version = rootProject.version.toString()
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
