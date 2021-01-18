package elements

import interaction.takeMouse
import interaction.takeScreen
import java.awt.Color

class CheckboxElement(name: String) : OptionElement<Boolean>(name) {
    override var height: Int = 48

    override fun readValue(x: Int, y: Int): Boolean {
        val valueX = x + width - valueWidth
        val capture = takeScreen().capture(valueX, y, valueWidth, height)

        val toggleColor = Color(capture.getRGB(241, 20), true)
        return UnlabelledSlider.isColorForEnabled(toggleColor)
    }

    override fun writeValue(x: Int, y: Int, value: Boolean) {
        val valueX = x + width - valueWidth
        val capture = takeScreen().capture(valueX, y, valueWidth, height)

        val toggleColor = Color(capture.getRGB(241, 20), true)
        val isEnabled = UnlabelledSlider.isColorForEnabled(toggleColor)

        if (value != isEnabled) {
            takeMouse().move(valueX + 241, y + 20).click()
        }
    }
}