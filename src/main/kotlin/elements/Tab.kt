package elements

import interaction.mouse
import logging.invoke

class Tab(
    val name: String,
    val buttonX: Int,
    val buttonY: Int = 90,
    val elementWidth: Int = 975,
    val valueWidth: Int = 468,
    val elements: List<Element>,
) {
    init {
        elements.forEach {
            it.width = elementWidth
            it.valueWidth = valueWidth
        }
    }

    fun select() = "Select tab <$name>" {
        mouse {
            move(buttonX, buttonY)
            click()
        }
    }
}