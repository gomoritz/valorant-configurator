package ocr

import logging.Logger
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

private val temporaryDirectory = File("temp").apply {
    mkdirs()
}

fun <T> BufferedImage.useTemporaryFile(action: (File) -> T): T {
    val uuid = UUID.randomUUID().toString()
    val fileName = "$uuid.png"
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
    val fileName = if (!replacePrevious) {
        val uuid = UUID.randomUUID().toString().take(6)
        "${label}__$uuid.png"
    } else {
        "$label.png"
    }.replace(": ", "_").replace("/", "_")
    val file = File(temporaryDirectory, fileName)

    ImageIO.write(this, "png", file)
    Logger.debug("Created debug file ${file.absolutePath} for $label")
}