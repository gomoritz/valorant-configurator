package elements

import interaction.takeMouse
import interaction.takeScreen
import java.awt.Color

const val SWITCH_VALUE_WIDTH = 232
const val SWITCH_VALUE_GAP = 4

class SwitchOptionElement(name: String) : OptionElement<Boolean>(name) {
    override fun readValue(x: Int, y: Int): Boolean {
        val valueX = x + width - (SWITCH_VALUE_WIDTH * 2) + SWITCH_VALUE_GAP

        val capture = takeScreen().capture(valueX, y, (SWITCH_VALUE_WIDTH * 2) + SWITCH_VALUE_GAP, height)
        val colorTrue = Color(capture.getRGB(1, 1), true)
        val colorFalse = Color(capture.getRGB(SWITCH_VALUE_WIDTH + SWITCH_VALUE_GAP + 1, 1), true)

        return colorTrue.compareBrightness(colorFalse) > 0
    }

    override fun writeValue(x: Int, y: Int, value: Boolean) {
        val valueX = x + width - (SWITCH_VALUE_WIDTH * 2) + SWITCH_VALUE_GAP
        val targetX = if (value) valueX + 1 else valueX + SWITCH_VALUE_WIDTH + SWITCH_VALUE_GAP + 1

        takeMouse().move(targetX, y + 1).click()
    }
}

private fun Color.compareBrightness(that: Color): Int {
    val avgThis = (red + green + blue) / 3
    val avgThat = (that.red + that.green + that.blue) / 3

    return avgThis.compareTo(avgThat)
}