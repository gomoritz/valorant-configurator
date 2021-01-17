package elements

import interaction.*
import ocr.*
import util.withClipboard
import java.awt.event.KeyEvent

const val FIELD_VALUE_WIDTH = 468

class FieldOptionElement(name: String) : OptionElement<Double>(name) {
    override fun readValue(x: Int, y: Int): Double {
        val valueX = x + width - FIELD_VALUE_WIDTH

        val text = takeScreen().capture(valueX, y, FIELD_VALUE_WIDTH, height)
            .makeTextReadable()
            .readText()

        return text.toDoubleOrNull() ?: 0.0
    }

    override fun writeValue(x: Int, y: Int, value: Double) {
        val valueX = x + width - FIELD_VALUE_WIDTH

        withClipboard(value.toString()) {
            takeMouse().move(valueX + FIELD_VALUE_WIDTH / 2, y + height / 2).click()
            takeKeyboard().press(KeyEvent.VK_CONTROL)
                .type(KeyEvent.VK_A)
                .type(KeyEvent.VK_V)
                .release(KeyEvent.VK_CONTROL)
                .type(KeyEvent.VK_ENTER)
        }
    }
}