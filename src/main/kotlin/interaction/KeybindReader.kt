package interaction

import elements.KEYBIND_VALUE_WIDTH
import logging.Logger
import ocr.*
import java.awt.*
import java.awt.image.BufferedImage

object KeybindReader {
    private val commonMistakes = mapOf(
        "ww" to "w",
        "fs" to "f5",
        "fi" to "f1",
        "ft" to "f7"
    )

    fun readKeybind(imageInput: BufferedImage): String? {
        val image = imageInput.getScaledInstance((KEYBIND_VALUE_WIDTH * 1.1).toInt(), imageInput.height, Image.SCALE_DEFAULT)
            .toBufferedImage().let {
                val marginTop = 10
                val marginLeft = 40
                it.getSubimage(marginLeft, marginTop, it.width - (marginLeft * 2), it.height - (marginTop * 2))
            }

        var text = image.readText()
        if (text == "-") return null

        if (text.toUpperCase().startsWith("F")) {
            tesseract.setTessVariable("tessedit_char_whitelist", "F0123456789")
            val rescannedText = increaseCharacterDistance(image).readText()
            tesseract.setTessVariable("tessedit_char_whitelist", DEFAULT_CHAR_WHITELIST)

            if (rescannedText[0] == 'F' && rescannedText.length > 1) {
                Logger.debug("Performed F-rescan on <$text> and got <$rescannedText>")
                text = rescannedText
            } else {
                Logger.debug("Performed F-rescan on <$text> and got <$rescannedText> - not using")
            }
        }

        if (text.length == 1 && text[0].isLowerCase()) {
            tesseract.setTessVariable("tessedit_char_whitelist", SINGLE_CHAR_WHITELIST)
            tesseract.setPageSegMode(10)
            val rescannedText = scaleForSingleCharacter(image).readText().getOrNull(0)?.toString()
            tesseract.setTessVariable("tessedit_char_whitelist", DEFAULT_CHAR_WHITELIST)
            tesseract.setPageSegMode(7)

            if (rescannedText != null) {
                Logger.debug("Performed single-lowercase-rescan for <$text> and got <$rescannedText>")
                text = rescannedText
            }
        }

        text = correctCommonMistakes(text)
        text = replaceIllegalCharacters(text)

        return text.takeUnless { it == "-" }?.toLowerCase()
    }

    private fun correctCommonMistakes(input: String): String {
        return commonMistakes[input.toLowerCase()] ?: input
    }

    private fun increaseCharacterDistance(image: BufferedImage): BufferedImage {
        val new = BufferedImage(image.width, image.height, image.type)
        val g = new.createGraphics()

        g.color = Color.WHITE
        g.fillRect(0, 0, new.width, new.height)

        var currentX = 25
        var fillStart = -1
        var fillWidth = 0

        for (x in 0 until image.width) {
            var isColumnFilled = false

            inner@for (y in 0 until image.height) {
                val color = Color(image.getRGB(x, y), true)
                if (color.red < 255 || color.green < 255 || color.blue < 255) {
                    isColumnFilled = true
                    break@inner
                }
            }

            if (isColumnFilled) {
                if (fillStart == -1) {
                    fillStart = x
                }
                fillWidth++
            } else if (fillStart != -1) {
                g.drawImage(image.getSubimage(fillStart, 0, fillWidth, image.height), currentX, 0, null)
                currentX += fillWidth + 4

                fillStart = -1
                fillWidth = 0
            }
        }

        g.dispose()
        return new
    }

    private fun scaleForSingleCharacter(image: BufferedImage): BufferedImage {
        val width = 30
        val subimage = image.getSubimage(image.width / 2 - width / 2, 0, width, image.height)

        val targetWidth = (subimage.width * 2.5).toInt()
        val targetHeight = (subimage.height * 2.1).toInt()

        val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.drawImage(subimage, 0, 0, targetWidth, targetHeight, null)
        graphics2D.dispose()
        return resizedImage
    }

    private fun replaceIllegalCharacters(str: String) = str.replace("é", "3")
}