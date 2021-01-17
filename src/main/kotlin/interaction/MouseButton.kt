package interaction

import java.awt.event.InputEvent

enum class MouseButton(internal val mask: Int) {
    LEFT(InputEvent.BUTTON1_DOWN_MASK),
    RIGHT(InputEvent.BUTTON3_DOWN_MASK),
    MIDDLE(InputEvent.BUTTON2_DOWN_MASK),
}