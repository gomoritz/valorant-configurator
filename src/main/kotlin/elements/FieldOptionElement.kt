package elements

import interaction.*
import ocr.*
import util.withClipboard
import java.awt.event.KeyEvent

class FieldOptionElement(name: String) : OptionElement<Double>(name) {
    override fun readValue(x: Int, y: Int): Double {
        val valueX = x + width - valueWidth

        val text = takeScreen().capture(valueX, y, valueWidth, height)
            .makeTextReadable()
            .readText()

        return text.toDoubleOrNull() ?: 0.0
    }

    override fun writeValue(x: Int, y: Int, value: Double) {
        val valueX = x + width - valueWidth

        withClipboard(value.toString()) {
            takeMouse().move(valueX + valueWidth / 2, y + height / 2).click()
            takeKeyboard().press(KeyEvent.VK_CONTROL)
                .type(KeyEvent.VK_A)
                .type(KeyEvent.VK_V)
                .release(KeyEvent.VK_CONTROL)
                .type(KeyEvent.VK_ENTER)
        }
    }
}