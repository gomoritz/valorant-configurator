package elements

class TabbedCategory(
    name: String,
    buttonX: Int,
    buttonY: Int,
    startY: Int,
    firstElementMargin: Int,
    windowHeight: Int,
    val tabs: List<Tab>,
) : Category(name, buttonX, buttonY, startY, firstElementMargin, windowHeight)