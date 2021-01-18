package ocr

import net.sourceforge.tess4j.Tesseract
import net.sourceforge.tess4j.util.LoadLibs
import java.awt.image.BufferedImage
import java.awt.Image

const val DEFAULT_CHAR_WHITELIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-. "
const val SINGLE_CHAR_WHITELIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

val tesseract: Tesseract = Tesseract().apply {
    val tessdataFolder = LoadLibs.extractTessResources("tessdata")
    setDatapath(tessdataFolder.absolutePath)

    setTessVariable("user_defined_dpi", "300")
    setTessVariable("tessedit_char_whitelist", DEFAULT_CHAR_WHITELIST)
}

fun BufferedImage.makeTextReadable() = invert().blackWhite()

fun BufferedImage.readText(): String {
    return tesseract.doOCR(this).removeSuffix("\n")
}

fun Image.toBufferedImage(): BufferedImage {
    if (this is BufferedImage) {
        return this
    }

    val bufferedImage = BufferedImage(getWidth(null), getHeight(null), BufferedImage.TYPE_INT_ARGB)
    val bGr = bufferedImage.createGraphics()
    bGr.drawImage(this, 0, 0, null)
    bGr.dispose()

    return bufferedImage
}
