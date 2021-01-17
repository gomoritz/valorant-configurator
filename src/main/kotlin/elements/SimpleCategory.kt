package elements

class SimpleCategory(
    name: String,
    buttonX: Int,
    buttonY: Int = 25,
    startY: Int,
    firstElementMargin: Int = 0,
    elementWidth: Int = 975,
    windowHeight: Int,
    val elements: List<Element>,
) : Category(name, buttonX, buttonY, startY, firstElementMargin, elementWidth, windowHeight) {
    init {
        elements.forEach { it.width = elementWidth }
    }
}