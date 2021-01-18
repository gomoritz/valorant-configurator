package elements

class TabbedCategory(
    name: String,
    buttonX: Int,
    buttonY: Int = 25,
    startY: Int,
    firstElementMargin: Int = 0,
    elementWidth: Int = 975,
    windowHeight: Int,
    val tabs: List<Tab>,
) : Category(name, buttonX, buttonY, startY, firstElementMargin, windowHeight)