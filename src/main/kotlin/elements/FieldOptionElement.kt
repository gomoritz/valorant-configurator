package elements

import interaction.*
import ocr.*
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

        takeMouse().move(valueX + FIELD_VALUE_WIDTH / 2, y + height / 2).click()
        takeKeyboard().press(KeyEvent.VK_CONTROL)
            .type(KeyEvent.VK_A)
            .release(KeyEvent.VK_CONTROL)
            .type(KeyEvent.VK_BACK_SPACE)
            .typeFloatingPoint(value)
            .type(KeyEvent.VK_ENTER)
    }
}