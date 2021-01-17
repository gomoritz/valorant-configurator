package interaction

import java.awt.Color
import java.awt.Rectangle
import java.awt.image.BufferedImage

interface Screen {
    val x: Int
    val y: Int
    val width: Int
    val height: Int

    fun readPixel(x: Int, y: Int): Color

    fun capture(rectangle: Rectangle = Rectangle(0, 0, width, height)): BufferedImage

    fun capture(x: Int, y: Int, width: Int, height: Int): BufferedImage =
        capture(Rectangle(x, y, width, height))
}