package ocr

import logging.Logger
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

private val temporaryDirectory = File("temp")

fun <T> BufferedImage.useTemporaryFile(action: (File) -> T): T {
    val uuid = UUID.randomUUID().toString()
    val fileName = "$uuid.png"
    temporaryDirectory.mkdirs()
    val file = File(temporaryDirectory, fileName)

    file.createNewFile()
    ImageIO.write(this, "png", file)
    Logger.debug("Created temporary file ${file.absolutePath}")

    try {
        return action(file)
    } finally {
        file.delete()
        Logger.debug("Deleted temporary file ${file.absolutePath}")
    }
}

fun BufferedImage.debugFile(label: String, replacePrevious: Boolean = true) = apply {
    try {
        val fileName = if (!replacePrevious) {
            val uuid = UUID.randomUUID().toString().take(6)
            "${label}__$uuid.png"
        } else {
            "$label.png"
        }.replace(": ", "_").replace("/", "_")
        temporaryDirectory.mkdirs()
        val file = File(temporaryDirectory, fileName)

        ImageIO.write(this, "png", file)
        Logger.debug("Created debug file ${file.absolutePath} for $label")
    } catch (e: Exception) {
        Logger.error("Failed to create debug file $label")
    }
}