package interaction

import java.awt.Robot

val robot = Robot().apply {
    autoDelay = 15
}

fun takeMouse() = Mouse
fun takeKeyboard() = Keyboard
fun takeScreen() = ValorantScreen

fun mouse(action: Mouse.() -> Unit) = takeMouse().action()
fun keyboard(action: Keyboard.() -> Unit) = takeKeyboard().action()