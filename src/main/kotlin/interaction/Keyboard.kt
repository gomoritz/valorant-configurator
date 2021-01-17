package interaction

import java.awt.event.KeyEvent

object Keyboard {
    fun press(keycode: Int) = apply {
        robot.keyPress(keycode)
    }

    fun release(keycode: Int) = apply {
        robot.keyRelease(keycode)
    }

    fun type(keycode: Int): Keyboard = apply {
        press(keycode).release(keycode)

        if (keycode == KeyEvent.VK_CAPS_LOCK) {
            Thread.sleep(100)
            press(keycode).release(keycode)
        }
    }

    fun typeFloatingPoint(value: Double) = apply {
        val string = value.toString()
        string.forEach { it.type() }
    }

    fun Char.type() = type(when (this) {
        '0' -> KeyEvent.VK_0
        '1' -> KeyEvent.VK_1
        '2' -> KeyEvent.VK_2
        '3' -> KeyEvent.VK_3
        '4' -> KeyEvent.VK_4
        '5' -> KeyEvent.VK_5
        '6' -> KeyEvent.VK_6
        '7' -> KeyEvent.VK_7
        '8' -> KeyEvent.VK_8
        '9' -> KeyEvent.VK_9
        '.' -> KeyEvent.VK_PERIOD
        else -> throw UnsupportedOperationException("Character '$this' is not supported")
    })
}