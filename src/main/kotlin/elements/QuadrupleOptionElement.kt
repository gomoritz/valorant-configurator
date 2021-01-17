package elements

import interaction.takeMouse
import interaction.takeScreen
import java.awt.Color

const val QUADRUPLE_VALUE_WIDTH = 144 // the third one is 144, all others 143

class QuadrupleOptionElement(name: String) : OptionElement<Int>(name) {
    override fun readValue(x: Int, y: Int): Int {
        val valueX = x + width - (QUADRUPLE_VALUE_WIDTH * 4) + 3

        val capture = takeScreen().capture(valueX, y, QUADRUPLE_VALUE_WIDTH * 4, height)
        val color0 = Color(capture.getRGB(1, 5), true)
        val color1 = Color(capture.getRGB(QUADRUPLE_VALUE_WIDTH + 1, 5), true)
        val color2 = Color(capture.getRGB((QUADRUPLE_VALUE_WIDTH * 2) + 1, 5), true)
        val color3 = Color(capture.getRGB((QUADRUPLE_VALUE_WIDTH * 3) + 1, 5), true)

        val colors = listOf(color0, color1, color2, color3)
        val brightest = colors.find { color -> colors.all { color == it || color.isBrighter(it) } }
        return colors.indexOf(brightest)
    }

    override fun writeValue(x: Int, y: Int, value: Int) {
        val valueX = x + width - (QUADRUPLE_VALUE_WIDTH * 4) + 3
        val targetX = valueX + (QUADRUPLE_VALUE_WIDTH * value)

        takeMouse().move(targetX + (QUADRUPLE_VALUE_WIDTH / 2), y + (height / 2)).click()
    }
}