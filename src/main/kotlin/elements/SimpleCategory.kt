package elements

class SimpleCategory(
    name: String,
    buttonX: Int,
    buttonY: Int,
    startY: Int,
    firstElementMargin: Int,
    val elementWidth: Int,
    val valueWidth: Int,
    windowHeight: Int,
    val elements: List<Element>,
) : Category(name, buttonX, buttonY, startY, firstElementMargin, windowHeight) {
    init {
        elements.forEach {
            it.width = elementWidth
            it.valueWidth = valueWidth
        }
    }
}