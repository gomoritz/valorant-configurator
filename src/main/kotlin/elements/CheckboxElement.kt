package elements

import interaction.takeMouse
import interaction.takeScreen
import java.awt.Color

const val CHECKBOX_VALUE_WIDTH = 482

class CheckboxElement(name: String) : OptionElement<Boolean>(name) {
    override val height: Int = 48

    override fun readValue(x: Int, y: Int): Boolean {
        val valueX = x + width - CHECKBOX_VALUE_WIDTH
        val capture = takeScreen().capture(valueX, y, CHECKBOX_VALUE_WIDTH, height)

        val toggleColor = Color(capture.getRGB(241, 20), true)
        return ToggleSliderElement.isColorForEnabled(toggleColor)
    }

    override fun writeValue(x: Int, y: Int, value: Boolean) {
        val valueX = x + width - CHECKBOX_VALUE_WIDTH
        val capture = takeScreen().capture(valueX, y, CHECKBOX_VALUE_WIDTH, height)

        val toggleColor = Color(capture.getRGB(241, 20), true)
        val isEnabled = ToggleSliderElement.isColorForEnabled(toggleColor)

        if (value != isEnabled) {
            takeMouse().move(valueX + 241, y + 20).click()
        }
    }
}