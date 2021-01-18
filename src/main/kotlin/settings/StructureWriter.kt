package settings

import elements.*
import org.jdom2.Document
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter
import java.io.File

typealias XMLNode = org.jdom2.Element

fun writeStructureTreeToXML() {
    val doc = Document()
    val root = XMLNode("settings-structure")

    ValorantSettingsTree.categories.forEach { category ->
        val node: XMLNode

        when (category) {
            is SimpleCategory -> {
                node = XMLNode("simple-category")
                visitElements(category.elements, node)
            }
            is TabbedCategory -> {
                node = XMLNode("tabbed-category")
                visitTabs(category.tabs, node)
            }
            else -> return@forEach
        }

        node.setAttribute("name", category.name)
        node.setAttribute("button-x", category.buttonX.toString())
        if (category.buttonY != 25)
            node.setAttribute("button-y", category.buttonY.toString())
        node.setAttribute("start-y", category.startY.toString())
        if (category.firstElementMargin != 0)
            node.setAttribute("first-element-margin", category.firstElementMargin.toString())
        node.setAttribute("window-height", category.windowHeight.toString())

        if (category is SimpleCategory) {
            if (category.elementWidth != 975)
                node.setAttribute("element-width", category.elementWidth.toString())
            if (category.valueWidth != 468)
                node.setAttribute("value-width", category.valueWidth.toString())
        }

        root.addContent(node)
    }

    doc.addContent(root)

    XMLOutputter().apply {
        format = Format.getPrettyFormat()
        output(doc, File("structure.xml").writer())
    }
}

private fun visitElements(elements: List<Element>, parent: XMLNode) {
    elements.forEach { element ->
        val tag = when (element) {
            is DropdownOptionElement -> "dropdown"
            is KeybindOptionElement -> "keybind"
            is QuadrupleOptionElement -> "quadruple"
            is SliderOptionElement -> "slider"
            is SwitchOptionElement -> "switch"
            is TripleOptionElement -> "triple"
            is UnlabelledSliderOptionElement -> "unlabelled-slider"
            is FieldOptionElement -> "field"
            is SectionElement -> "section"
            else -> return@forEach
        }

        val node = XMLNode(tag)
        node.addContent(element.name)

        if ((element is SectionElement && element.height != 66) ||
            (element is OptionElement<*> && element.height != 52)
        ) {
            node.setAttribute("height", element.height.toString())
        }

        if (element.shouldSkip) {
            node.setAttribute("skip", "true")
        }

        if (element is UnlabelledSliderOptionElement && !element.canToggle) {
            node.setAttribute("can-toggle", element.canToggle.toString())
        }

        parent.addContent(node)
    }
}

private fun visitTabs(tabs: List<Tab>, parent: XMLNode) = tabs.forEach { tab ->
    val node = XMLNode("tab")
    visitElements(tab.elements, node)

    node.setAttribute("name", tab.name)
    node.setAttribute("button-x", tab.buttonX.toString())
    if (tab.buttonY != 90)
        node.setAttribute("button-y", tab.buttonY.toString())
    if (tab.elementWidth != 975)
        node.setAttribute("element-width", tab.elementWidth.toString())
    if (tab.valueWidth != 468)
        node.setAttribute("value-width", tab.valueWidth.toString())

    parent.addContent(node)
}