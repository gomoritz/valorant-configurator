package display

import elements.Element
import interaction.takeScreen
import java.awt.*
import javax.swing.JPanel

object Display {
    lateinit var window: Window
    lateinit var panel: JPanel

    var positionedElement: PositionedElement? = null

    fun init() {
        window = Window(null).apply {
            isAutoRequestFocus = true
            isAlwaysOnTop = true
            background = Color(0, true)
            bounds = graphicsConfiguration.bounds
        }

        panel = object : JPanel() {
            override fun paint(g: Graphics): Unit = with(g as Graphics2D) {
                setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

                renderValorantBB()

                positionedElement?.let {
                    color = Color.GREEN
                    stroke = BasicStroke(1f)
                    drawRect(
                        takeScreen().x + it.x + it.element.width - it.element.valueWidth,
                        takeScreen().y + it.y,
                        it.element.valueWidth,
                        it.element.height
                    )

                    color = Color.BLUE
                    drawRect(
                        takeScreen().x + it.x - 1,
                        takeScreen().y + it.y,
                        it.element.width - it.element.valueWidth,
                        it.element.height
                    )
                }
            }
        }.apply {
            background = Color(0, true)
            bounds = window.graphicsConfiguration.bounds
            isVisible = true
        }

        window.add(panel)
        window.isVisible = true
    }

    fun highlightElement(pos: PositionedElement?) {
        this.positionedElement = pos
        panel.isVisible = false
        panel.isVisible = true
        window.requestFocus()
    }
}

data class PositionedElement(val x: Int, val y: Int, val element: Element)