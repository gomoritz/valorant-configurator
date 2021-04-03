import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    application
}

group = "net.theincxption"
version = "2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.sourceforge.tess4j:tess4j:4.5.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.11.3")
    implementation("org.jdom:jdom:2.0.0")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
    implementation("com.1stleg:jnativehook:2.1.0")
}

application {
    mainClass.set("ValorantConfigurator")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "15"
}