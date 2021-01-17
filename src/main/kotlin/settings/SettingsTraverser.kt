package settings

import display.Display
import display.ElementPosition
import elements.*
import interaction.*
import logging.invoke
import ocr.tesseract

typealias ElementAction = (element: OptionElement<*>, x: Int, y: Int) -> Unit

const val DISTANCE_PER_SCROLL = 100

class SettingsTraverser(private val action: ElementAction) {

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

        "Traverse settings" {
            val categories = ValorantSettingsTree.categories

            for (category in categories) {
                category.select()

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
            "Traverse elements in tab <${tab.name}>" {
                traverseElements(category, tab.elements)
            }
        }
    }

    private fun traverseElements(category: Category, elements: List<Element>) {
        "Reset scrollbar" {
            mouse {
                move(takeScreen().width / 2, takeScreen().height / 2)
                wheel(-30)
                hideCursor()
            }
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
                Display.highlightElement(ElementPosition(elementX, elementY - scrollOffset, element.height, element.width))
                action(element, elementX, elementY - scrollOffset)
                Display.highlightElement(null)
            }

            elementY += element.height
        }

        Display.highlightElement(null)
    }

    private fun scrollDown() = takeMouse()
        .move(takeScreen().width / 2, 400)
        .wheel(1)
}