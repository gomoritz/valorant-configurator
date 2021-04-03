package interaction

object Mouse {

    fun move(x: Int, y: Int) = apply {
        robot.mouseMove(x + takeScreen().x, y + takeScreen().y)
    }

    fun press(button: MouseButton) = apply {
        robot.mousePress(button.mask)
    }

    fun release(button: MouseButton) = apply {
        robot.mouseRelease(button.mask)
    }

    fun wheel(amount: Int) = apply {
        robot.mouseWheel(amount)
    }

    fun click(button: MouseButton = MouseButton.LEFT) = press(button).release(button)

    fun hideCursor() {
        (0..20 step 4).forEach { move(it, ValorantScreen.height - 5) }
        click()
    }
}