package elements

import interaction.mouse
import logging.invoke

abstract class Category(
    val name: String,
    val buttonX: Int,
    val buttonY: Int,
    val startY: Int,
    val firstElementMargin: Int,
    val elementWidth: Int = 975,
    val windowHeight: Int,
) {
    fun select() = "Select category <$name>" {
        mouse {
            move(buttonX, buttonY)
            click()
        }
    }
}