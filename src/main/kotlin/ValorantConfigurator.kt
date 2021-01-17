import display.Display
import interaction.*
import logging.Logger
import logging.invoke
import ocr.*
import settings.readGeneralSettings
import settings.writeGeneralSettings
import java.awt.Robot
import java.io.File
import kotlin.system.exitProcess

fun main() {
    Thread.sleep(1_500)
    Display.init()

//    readGeneralSettings(File("config.json"))
    writeGeneralSettings(File("config.json"))
}