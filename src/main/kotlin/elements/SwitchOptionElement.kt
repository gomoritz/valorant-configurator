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

        return colorTrue.isBrighter(colorFalse)
    }

    override fun writeValue(x: Int, y: Int, value: Boolean) {
        val valueX = x + width - (SWITCH_VALUE_WIDTH * 2) + SWITCH_VALUE_GAP
        val targetX = if (value) valueX + 1 else valueX + SWITCH_VALUE_WIDTH + SWITCH_VALUE_GAP + 1

        takeMouse().move(targetX + (SWITCH_VALUE_WIDTH / 2), y + height / 2).click()
    }
}

fun Color.isBrighter(that: Color): Boolean {
    val avgThis = (red + green + blue) / 3
    val avgThat = (that.red + that.green + that.blue) / 3

    return avgThis > avgThat
}