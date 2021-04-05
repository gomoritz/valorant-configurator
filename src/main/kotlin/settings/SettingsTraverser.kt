package settings

import display.Display
import display.PositionedElement
import elements.*
import interaction.*
import logging.invoke
import ocr.tesseract

typealias ElementAction = (element: OptionElement<*>, x: Int, y: Int) -> Unit
typealias SectionHandler = (element: SectionElement) -> Unit
typealias CategoryHandler = (category: Category) -> Unit
typealias TabHandler = (tab: Tab) -> Unit

const val DISTANCE_PER_SCROLL = 100

class SettingsTraverser(private val structure: StructureTree, private val action: ElementAction) {

    private var sectionHandler: SectionHandler? = null
    private var categoryHandler: CategoryHandler? = null
    private var tabHandler: TabHandler? = null

    companion object {
        var totalElements = -1
            private set
    }

    fun start() {
        "Open settings" {
            val screen = takeScreen()
            mouse {
                move(screen.width - 30, 30)
                click()
                move(screen.width / 2, 430)
                click()

                hideCursor()
            }
        }

        totalElements = structure.sumBy {
            when (it) {
                is SimpleCategory -> it.elements.size
                is TabbedCategory -> it.tabs.sumBy { tab -> tab.elements.size }
                else -> 0
            }
        }

        "Traverse settings" {
            for (category in structure) {
                category.select()
                categoryHandler?.invoke(category)

                if (category is SimpleCategory) {
                    "Traverse elements in category <${category.name}>" {
                        traverseElements(category, category.elements)
                    }
                } else if (category is TabbedCategory) {
                    traverseTabs(category, category.tabs)
                }
            }
        }
    }

    private fun traverseTabs(category: TabbedCategory, tabs: List<Tab>) = "Traverse tabs in category <${category.name}>" {
        tabs.forEach { tab ->
            tab.select()
            tabHandler?.invoke(tab)
            "Traverse elements in tab <${tab.name}>" {
                traverseElements(category, tab.elements)
            }
        }
    }

    private fun traverseElements(category: Category, elements: List<Element>) {
        mouse {
            move(takeScreen().width / 2, takeScreen().height / 2)
            wheel(-30)
            hideCursor()
        }

        tesseract.setPageSegMode(7)

        val elementX = 460
        var elementY = category.startY

        var scrollOffset = -category.firstElementMargin
        val fullHeight = elements.sumBy { it.height }

        for (element in elements) {
            if (elementY + element.height - category.startY - scrollOffset > category.windowHeight) {
                scrollDown()
                scrollOffset += DISTANCE_PER_SCROLL
                scrollOffset = scrollOffset.coerceAtMost(fullHeight - category.windowHeight)
            }

            if (element !is SectionElement && element is OptionElement<*> && !element.shouldSkip) {
                Display.highlightElement(PositionedElement(elementX, elementY - scrollOffset, element))
                action(element, elementX, elementY - scrollOffset)
                Display.highlightElement(null)
            } else if (element is SectionElement) {
                sectionHandler?.invoke(element)
            }

            elementY += element.height
            Display.elementIndex++
        }

        Display.highlightElement(null)
    }

    private fun scrollDown() = takeMouse()
        .move(takeScreen().width / 2, 400)
        .wheel(1)

    fun handleSection(handler: SectionHandler) = apply {
        sectionHandler = handler
    }

    fun handleCategory(handler: CategoryHandler) = apply {
        categoryHandler = handler
    }

    fun handleTab(handler: TabHandler) = apply {
        tabHandler = handler
    }
}