package elements

import interaction.takeMouse
import interaction.takeScreen
import ocr.debugFile
import java.awt.Color

const val TRIPLE_VALUE_WIDTH = 157 // the first one is only 156

class TripleOptionElement(name: String) : OptionElement<Int>(name) {
    override fun readValue(x: Int, y: Int): Int {
        val valueX = x + width - (TRIPLE_VALUE_WIDTH * 3) + 1 // +1 because: comment above

        val capture = takeScreen().capture(valueX, y, TRIPLE_VALUE_WIDTH * 3, height)
        val color0 = Color(capture.getRGB(1, 1), true)
        val color1 = Color(capture.getRGB(TRIPLE_VALUE_WIDTH + 1, 1), true)
        val color2 = Color(capture.getRGB((TRIPLE_VALUE_WIDTH * 2) + 1, 1), true)

        val colors = listOf(color0, color1, color2)
        val brightest = colors.find { color -> colors.all { color == it || color.isBrighter(it) } }
        return colors.indexOf(brightest)
    }

    override fun writeValue(x: Int, y: Int, value: Int) {
        val valueX = x + width - (TRIPLE_VALUE_WIDTH * 3) + 1 // +1 because: comment above
        val targetX = valueX + 5 + (TRIPLE_VALUE_WIDTH * value)

        takeMouse().move(targetX + (TRIPLE_VALUE_WIDTH / 2), y + height / 2).click()
    }
}