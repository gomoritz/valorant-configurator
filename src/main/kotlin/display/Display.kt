package display

import elements.Element
import interaction.takeScreen
import settings.SettingsTraverser
import java.awt.*
import javax.swing.JPanel

object Display {
    lateinit var window: Window
    lateinit var panel: JPanel

    var positionedElement: PositionedElement? = null
    var elementIndex: Int = -1
    var progress: Double = 0.0

    fun init() {
        window = Window(null).apply {
            isAutoRequestFocus = true
            isAlwaysOnTop = true
            background = Color(0, true)
            bounds = graphicsConfiguration.bounds
        }

        panel = object : JPanel() {
            override fun paint(g: Graphics): Unit = with(g as Graphics2D) {
                val screen = takeScreen()
                setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

                renderValorantBB()

                positionedElement?.let {
                    color = Color.GREEN
                    stroke = BasicStroke(1f)
                    drawRect(
                        screen.x + it.x,
                        screen.y + it.y,
                        it.element.width,
                        it.element.height
                    )

                    color = Color(0, 255, 0, 50)
                    fillRect(
                        screen.x + it.x + 1,
                        screen.y + it.y + 1,
                        it.element.width - it.element.valueWidth - 2,
                        it.element.height - 2
                    )
                }

                color = Color(0, 0, 100)
                fillRect(screen.x, screen.y + screen.height - 3, screen.width, 3)

                color = Color(0, 0, 255)
                fillRect(screen.x, screen.y + screen.height - 3, (screen.width * progress).toInt(), 3)
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

        if (pos != null) {
            progress = elementIndex / SettingsTraverser.totalElements.toDouble()
        }

        panel.isVisible = false
        panel.isVisible = true
        window.requestFocus()
    }
}

data class PositionedElement(val x: Int, val y: Int, val element: Element)