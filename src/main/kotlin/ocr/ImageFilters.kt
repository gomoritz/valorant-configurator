package ocr

import java.awt.Color
import java.awt.color.ColorSpace
import java.awt.image.BufferedImage
import java.awt.image.ColorConvertOp

fun BufferedImage.greyscale(): BufferedImage {
    val op = ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
    return op.filter(this, this)
}

fun BufferedImage.blackWhite(): BufferedImage {
    val blackWhite = BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)
    blackWhite.createGraphics().apply {
        drawImage(this@blackWhite, 0, 0, null)
        dispose()
    }
    return blackWhite
}

fun BufferedImage.invert(): BufferedImage {
    for (x in 0 until width) {
        for (y in 0 until height) {
            val rgba = getRGB(x, y)
            var color = Color(rgba, true)
            color = Color(
                255 - color.red,
                255 - color.green,
                255 - color.blue
            )
            setRGB(x, y, color.rgb)
        }
    }
    return this
}