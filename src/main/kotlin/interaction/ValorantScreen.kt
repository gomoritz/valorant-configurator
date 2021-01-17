package interaction

import logging.Logger
import java.awt.Color
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.lang.IllegalStateException

object ValorantScreen : Screen {
    private val screenOffset = getScreenOffset()

    override val x: Int = screenOffset
    override val y: Int = 0
    override val width: Int = DisplayScreen.width - (screenOffset * 2)
    override val height: Int = DisplayScreen.height

    override fun readPixel(x: Int, y: Int): Color =
        robot.getPixelColor(this.x + x, this.y + y)

    override fun capture(rectangle: Rectangle): BufferedImage {
        val relativeRectangle = Rectangle(rectangle.x + this.x,
            rectangle.y + this.y,
            rectangle.width,
            rectangle.height)
        return robot.createScreenCapture(relativeRectangle)
    }
}

private fun getScreenOffsetLeft(capture: BufferedImage): Int {
    for (x in 0 until capture.width) {
        for (y in 0 until capture.height step 100) {
            if (capture.getRGB(x, y) != Color.BLACK.rgb)
                return x
        }
    }

    throw IllegalStateException("The whole screen is black :o")
}

private fun getScreenOffsetRight(capture: BufferedImage): Int {
    for (x in capture.width - 1 downTo 0) {
        for (y in 0 until capture.height step 100) {
            if (capture.getRGB(x, y) != Color.BLACK.rgb)
                return capture.width - 1 - x
        }
    }

    throw IllegalStateException("The whole screen is black :o")
}

private fun getScreenOffset(): Int {
    Logger.debug("Calculating Valorant screen offset...")
    val capture = DisplayScreen.capture()
    val left = getScreenOffsetLeft(capture)
    val right = getScreenOffsetRight(capture)

    require(left == right) { "DisplayScreen offset left != screen offset right" }

    Logger.info("Valorant screen offset: $left")
    return left
}