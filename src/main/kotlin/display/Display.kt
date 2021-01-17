package display

import interaction.takeScreen
import java.awt.*
import javax.swing.JPanel

object Display {
    lateinit var window: Window
    lateinit var panel: JPanel

    var elementPosition: ElementPosition? = null

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

                elementPosition?.let {
                    color = Color.GREEN
                    stroke = BasicStroke(1f)
                    drawRect(takeScreen().x + it.x, takeScreen().y + it.y, it.width, it.height)
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

    fun highlightElement(pos: ElementPosition?) {
        this.elementPosition = pos
        panel.isVisible = false
        panel.isVisible = true
        window.requestFocus()
    }
}

data class ElementPosition(val x: Int, val y: Int, val height: Int, val width: Int)