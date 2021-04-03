package interaction

import java.awt.event.InputEvent

sealed class MouseButton(internal val mask: Int) {
    object LEFT : MouseButton(InputEvent.BUTTON1_DOWN_MASK)
    object RIGHT : MouseButton(InputEvent.BUTTON3_DOWN_MASK)
    object MIDDLE : MouseButton(InputEvent.BUTTON2_DOWN_MASK)
    class ThumbButton(mask: Int) : MouseButton(mask)
}