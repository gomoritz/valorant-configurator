package interaction

import java.awt.*
import java.awt.image.BufferedImage

object DisplayScreen : Screen {
    private val graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
    private val graphicsDevice = graphicsEnvironment.defaultScreenDevice

    override val x: Int = 0
    override val y: Int = 0
    override val width: Int = graphicsDevice.displayMode.width
    override val height: Int = graphicsDevice.displayMode.height

    override fun readPixel(x: Int, y: Int): Color =
        robot.getPixelColor(x, y)

    override fun capture(rectangle: Rectangle): BufferedImage =
        robot.createScreenCapture(rectangle)
}