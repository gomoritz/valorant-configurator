package elements

import interaction.*
import ocr.*
import util.withClipboard
import java.awt.event.KeyEvent

const val SLIDER_TEXT_WIDTH = 110
const val SLIDER_VALUE_WIDTH = 468

class SliderOptionElement(name: String) : OptionElement<Double>(name) {
    override fun readValue(x: Int, y: Int): Double {
        val valueX = x + width - SLIDER_VALUE_WIDTH

        val text = takeScreen().capture(valueX, y, SLIDER_TEXT_WIDTH, height)
            .makeTextReadable()
            .readText()

        return text.toDoubleOrNull() ?: 0.0
    }

    override fun writeValue(x: Int, y: Int, value: Double) {
        val valueX = x + width - SLIDER_VALUE_WIDTH

        withClipboard(value.toString()) {
            takeMouse().move(valueX + SLIDER_TEXT_WIDTH / 2, y + height / 2).click()
            takeKeyboard().press(KeyEvent.VK_CONTROL)
                .type(KeyEvent.VK_A)
                .type(KeyEvent.VK_V)
                .release(KeyEvent.VK_CONTROL)
                .type(KeyEvent.VK_ENTER)
        }
    }
}