@file:JvmName("ValorantConfigurator")

import display.Display
import logging.Logger
import settings.*
import java.awt.Desktop
import java.io.File
import java.util.*
import kotlin.system.exitProcess

private val file = File(System.getProperty("user.home"), "config.valo").absoluteFile

fun main() {
    try {
        println()
        println("> Enter one of the following options to execute the Valorant Configurator:")
        println("  - 'R': Read settings from Valorant and write into the config.json")
        println("  - 'W': Write settings from the config.json into the Valorant client")
        println()

        while (true) {
            when (readLine()) {
                "r", "R" -> if (executeRead()) break
                "w", "W" -> if (executeWrite()) break
                else -> println("! Invalid option, please try again.")
            }
        }

        Logger.info("Finished!")
        Timer().schedule(object : TimerTask() {
            override fun run() {
                exitProcess(0)
            }
        }, 1000L)

        try {
            Desktop.getDesktop().browse(file.toURI())
        } catch (e: Exception) {
            Logger.warn("Couldn't open config.valo")
            e.printStackTrace()
        }
    } catch (e: Exception) {
        Logger.error("An error occurred while executing the Valorant Configurator")
        e.printStackTrace()
        exitProcess(1)
    }
}

private fun prependCountdown(): Boolean {
    println()
    println("> Preparing to read settings from the Valorant client...")
    println("  - Make sure you switched to 'Windowed Fullscreen'")
    println("  - Select 'English (United States)' as the language")
    println("  - Be ready to enter the game when the countdown starts")
    println()
    println("> Enter 'Y' to start, or anything else to abort")
    println()

    if (readLine()?.toUpperCase() == "Y") {
        println("")
        for (i in 5 downTo 0) {
            println("> $i...")
            Thread.sleep(1000)
        }
        println()
        println("=== STARTING VALORANT CONFIGURATOR ===")
        println()
        return true
    }

    return false
}

private fun executeRead(): Boolean {
    if (!prependCountdown()) return false

    Display.init()
    readSettings(parseStructureFromXML(), file)
    return true
}

private fun executeWrite(): Boolean {
    if (!prependCountdown()) return false

    Display.init()
    writeSettings(parseStructureFromXML(), file)
    return true
}