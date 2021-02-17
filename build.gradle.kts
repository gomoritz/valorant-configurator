import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    id("org.beryx.runtime") version "1.12.1"
}

group = "net.theincxption"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.sourceforge.tess4j:tess4j:4.5.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.11.3")
    implementation("org.jdom:jdom:2.0.0")
}

application {
    mainClass.set("launcher.Launcher")
}

runtime {
    modules.set(listOf("java.logging", "java.desktop", "java.datatransfer", "java.xml"))

    jpackage {
        imageName = "Valorant Configurator"
//        imageDir.set(File("./build/Valorant Configurator"))
//        imageOutputDir = File("./build/Valorant Configurator")

        installerType = "msi"
        installerOptions = listOf(
            "--name", "\"Valorant Configurator\"",
            "--win-shortcut",
            "--win-dir-chooser",
            "--win-menu",
            "--win-menu-group", "\"Inception Cloud\"",
            "--description", "\"Copy & paste settings for Valorant\"",
            "--vendor", "\"Inception Cloud\"",
            "--file-associations", "FAvalo.properties"
        )
        imageOptions = listOf(
            "--name", "\"Valorant Configurator\"",
            "--icon", "icon.ico"
        )
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "15"
}