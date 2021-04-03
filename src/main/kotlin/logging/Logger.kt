package logging

import java.io.File
import java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE
import java.text.SimpleDateFormat

object Logger {
    private val logFile = File("valorant_configurator.log")
    private val stackWalker = StackWalker.getInstance(RETAIN_CLASS_REFERENCE)
    private val template = "${Color.BLACK_BRIGHT}[${Color.WHITE}%s${Color.BLACK_BRIGHT}] " +
            "[%s${Color.BLACK_BRIGHT}] " +
            "${Color.PURPLE_BRIGHT}%s%s " +
            "${Color.BLACK_BRIGHT}- " +
            "${Color.WHITE_BRIGHT}%s"
    private const val templateWithoutColors = "[%s] [%s] %s%s - %s"

    private fun log(level: Level, message: Any?, logger: String) {
        val marginSize = 5 - level.name.length
        val margin = (1..marginSize).joinToString { " " }
        val date = SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())

        val withHighlights = message.toString().replace(Regex("<(.*?)>"), "${Color.CYAN_BRIGHT}$1${Color.WHITE_BRIGHT}")
        val formatted = template.format(date, level.color.toString() + level.name, margin, logger, withHighlights)
        val formattedWithoutColors = templateWithoutColors.format(date, level.name, margin, logger, message)

        println(formatted)
        logFile.appendText(formattedWithoutColors + "\n")
    }

    fun debug(message: Any?, logger: String = stackWalker.callerClass.simpleName) = log(Level.DEBUG, message, logger)
    fun info(message: Any?, logger: String = stackWalker.callerClass.simpleName) = log(Level.INFO, message, logger)
    fun warn(message: Any?, logger: String = stackWalker.callerClass.simpleName) = log(Level.WARN, message, logger)
    fun error(message: Any?, logger: String = stackWalker.callerClass.simpleName) = log(Level.ERROR, message, logger)
}

