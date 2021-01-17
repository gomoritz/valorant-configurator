package display

import interaction.ValorantScreen
import java.awt.*

fun Graphics2D.renderValorantBB() {
    val strokeWidth = 2
    stroke = BasicStroke(strokeWidth.toFloat())
    color = Color.RED
    font = Font("SF Pro Display", Font.PLAIN, 20)

    with(ValorantScreen) {
        drawRect(
            x + strokeWidth / 2,
            y + strokeWidth / 2,
            width - strokeWidth,
            height - strokeWidth
        )
        drawString("Valorant Configurator", x + 15, y + 30)
    }
}