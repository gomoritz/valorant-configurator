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

        return when {
            color0.isBrighter(color1) && color0.isBrighter(color2) -> 0
            color1.isBrighter(color0) && color0.isBrighter(color2) -> 1
            color2.isBrighter(color0) && color0.isBrighter(color1) -> 2
            else -> throw IllegalStateException("Cannot read value of triple option")
        }
    }

    override fun writeValue(x: Int, y: Int, value: Int) {
        val valueX = x + width - (TRIPLE_VALUE_WIDTH * 3) + 1 // +1 because: comment above
        val targetX = valueX + 5 + (TRIPLE_VALUE_WIDTH * value)

        takeMouse().move(targetX, y + 1).click()
    }
}

private fun Color.isBrighter(that: Color): Boolean {
    val avgThis = (red + green + blue) / 3
    val avgThat = (that.red + that.green + that.blue) / 3

    return avgThis > avgThat
}