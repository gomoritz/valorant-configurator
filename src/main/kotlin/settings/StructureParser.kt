package settings

import elements.*
import logging.invoke
import org.jdom2.input.SAXBuilder
import java.io.File

fun parseStructureFromXML(): List<Category> = "Parse structure from XML" {
    val file = File("structure.xml")
    val doc = SAXBuilder().build(file)

    val root = doc.rootElement
    require(root.name == "settings-structure") { "File doesn't contain settings-structure node as root" }

    visitCategories(root)
}

private fun visitCategories(root: XMLNode): List<Category> = "Parse categories" {
    root.children.mapNotNull { node ->
        val name = node.getAttributeValue("name")!!
        val buttonX = node.getAttributeValue("button-x").toInt()
        val buttonY = node.getAttributeValue("button-y")?.toIntOrNull() ?: 25
        val startY = node.getAttributeValue("start-y").toInt()
        val firstElementMargin = node.getAttributeValue("first-element-margin")?.toIntOrNull() ?: 0
        val windowHeight = node.getAttributeValue("window-height").toInt()

        when(node.name) {
            "simple-category", "sc" -> {
                val elementWidth = node.getAttributeValue("element-width")?.toIntOrNull() ?: 975
                val valueWidth = node.getAttributeValue("value-width")?.toIntOrNull() ?: 468
                val elements = visitElements(node)

                SimpleCategory(
                    name, buttonX, buttonY, startY, firstElementMargin, elementWidth,
                    valueWidth, windowHeight, elements
                )
            }
            "tabbed-category", "tc" -> {
                val tabs = visitTabs(node)

                TabbedCategory(
                    name, buttonX, buttonY, startY, firstElementMargin, windowHeight, tabs
                )
            }
            else -> null
        }
    }
}

private fun visitTabs(parent: XMLNode): List<Tab> = "Parse tabs of ${parent.getAttributeValue("name")}" {
    parent.children.mapNotNull { node ->
        val name = node.getAttributeValue("name")!!
        val buttonX = node.getAttributeValue("button-x").toInt()
        val buttonY = node.getAttributeValue("button-y")?.toIntOrNull() ?: 90
        val elementWidth = node.getAttributeValue("element-width")?.toIntOrNull() ?: 975
        val valueWidth = node.getAttributeValue("value-width")?.toIntOrNull() ?: 468

        Tab(name, buttonX, buttonY, elementWidth, valueWidth, visitElements(node))
    }
}

private fun visitElements(parent: XMLNode): List<Element> = "Parse elements of ${parent.getAttributeValue("name")}" {
    parent.children.mapNotNull { node ->
        val height: Int? = node.getAttributeValue("height")?.toIntOrNull()
        val skip: Boolean = node.getAttributeValue("skip") == "true"
        val name = node.text

        val element = when(node.name) {
            "dropdown", "drop", "d" -> DropdownOptionElement(name)
            "keybind", "key", "k" -> KeybindOptionElement(name)
            "quadruple", "quad", "q" -> QuadrupleOptionElement(name)
            "slider", "sl" -> SliderOptionElement(name)
            "switch", "sw" -> SwitchOptionElement(name)
            "triple", "t" -> TripleOptionElement(name)
            "unlabelled-slider", "u" -> UnlabelledSliderOptionElement(name, canToggle = node.getAttributeValue("can-toggle") != "false")
            "field", "f" -> FieldOptionElement(name)
            "section", "sec" -> SectionElement(name)
            "checkbox", "cb", "c" -> CheckboxElement(name)
            else -> return@mapNotNull null
        }

        if (height != null) element.height = height
        if (skip) element.skip()

        element
    }
}