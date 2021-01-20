package elements

import interaction.takeMouse
import interaction.takeScreen
import logging.Logger
import ocr.debugFile
import java.awt.Color

const val DROPDOWN_ITEM_HEIGHT = 18
const val READABLE_DROPDOWN_ITEMS = 10

class DropdownOptionElement(name: String) : OptionElement<Int>(name) {
    override fun readValue(x: Int, y: Int): Int {
        val valueX = x + width - valueWidth
        val textWidth = valueWidth - 35

        takeMouse().move(valueX + valueWidth / 2, y + height / 2).click()
        val capture = takeScreen().capture(valueX, y, textWidth, height + READABLE_DROPDOWN_ITEMS * DROPDOWN_ITEM_HEIGHT)
        val colors = mutableListOf<Color>()

        for (i in 0 until READABLE_DROPDOWN_ITEMS) {
            val itemY = height + (i * DROPDOWN_ITEM_HEIGHT)
            val color = Color(capture.getRGB(textWidth - 3, itemY + 1), true)
            colors.add(color)

            if (color.isSelectedColor()) {
                takeMouse().hideCursor()
                return i
            }
        }

        Logger.error("Cannot read value of dropdown option $name")
        Logger.debug("Colors: " + colors.withIndex().joinToString { "(${it.index}) ${it.value}" })
        capture.debugFile("failed-dropdown__$name")
        takeMouse().hideCursor()
        return 0
    }

    override fun writeValue(x: Int, y: Int, value: Int) {
        val valueX = x + width - valueWidth
        takeMouse()
            .move(valueX + 10, y + 10).click()
            .move(valueX + 1, y + height + (value * DROPDOWN_ITEM_HEIGHT) + 1).click()
    }
}

private fun Color.isSelectedColor(): Boolean =
    red >= 220 && green >= 220 && blue in 165..185