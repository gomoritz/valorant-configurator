package display

import interaction.ValorantScreen
import java.awt.*

fun Graphics2D.renderValorantBB() {
    val strokeWidth = 2
    stroke = BasicStroke(strokeWidth.toFloat())
    color = MAIN_COLOR
    font = Font("SF Pro Display", Font.BOLD, 30)

    with(ValorantScreen) {
        drawRect(
            x + strokeWidth / 2,
            y + strokeWidth / 2,
            width - strokeWidth,
            height
        )
        drawString("Valorant Configurator 2.0", x + 15, y + 38)

        font = Font("SF Pro Display", Font.PLAIN, 20)
        color = HIGHLIGHT_COLOR
        drawString("Press Ctrl + S to cancel", x + 15, y + 80)
    }
}