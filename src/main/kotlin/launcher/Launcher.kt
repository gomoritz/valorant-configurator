@file:JvmName("Launcher")

package launcher

import logging.Logger
import java.io.File

fun main(args: Array<String>) {
    val targetFile = args.getOrNull(0)
    val javaBin = File("runtime\\bin")
    val isProduction = javaBin.exists() && javaBin.isDirectory
    Logger.debug("Is production: <$isProduction>")
    Logger.debug("Target file: <$targetFile>")

    val javaPath: String
    val libDir: String

    if (isProduction) {
        javaPath = File(javaBin, "java.exe").absolutePath
        libDir = File("app\\").absolutePath
    } else {
        javaPath = System.getenv("JAVA_HOME") + "\\bin\\java.exe"
        libDir = File("build\\install\\valorant-configurator\\lib\\").absolutePath
    }

    Logger.debug("Java path: <$javaPath>")
    Logger.debug("Libraries directory: <$libDir>")

    val command = mutableListOf("cmd", "/c", "start", "cmd", "/k", javaPath, "-cp", "$libDir\\*", "ValorantConfigurator")

    targetFile?.let {
        command.add("--target-file")
        command.add(it)
    }

    val exitCode = ProcessBuilder()
        .command(command)
        .inheritIO()
        .start()
        .waitFor()
    Logger.info("Process finished with exit code <$exitCode>")
}