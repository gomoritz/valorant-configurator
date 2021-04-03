package interaction

import logging.Logger
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*

object KeybindWriter {
    fun produceInput(descriptor: String): Boolean {
        translateMouseWheel(descriptor)?.let {
            takeMouse().wheel(it)
            return true
        }
        translateMouseButton(descriptor)?.let {
            takeMouse().click(it)
            return true
        }
        translateKey(descriptor)?.let {
            takeKeyboard().type(it)
            return true
        }

        Logger.warn("Could not produce input for descriptor <$descriptor>")
        return false
    }

    private fun translateKey(descriptor: String): Int? {
        if (descriptor.isSingleLetterOrNumber()) {
            return findKeyEventConstant("VK_${descriptor[0].toUpperCase()}")
        }

        descriptor.parseFKey()?.let {
            return findKeyEventConstant("VK_F$it")
        }

        descriptor.parseNumKey()?.let {
            return findKeyEventConstant("VK_NUMPAD$it")
        }

        return translateSpecialKey(descriptor)
    }

    private fun translateMouseButton(descriptor: String): MouseButton? {
        when (descriptor) {
            "left mouse button" -> return MouseButton.LEFT
            "right mouse button" -> return MouseButton.RIGHT
            "middle mouse button" -> return MouseButton.MIDDLE
            else -> {
                val prefix = "thumb mouse button"
                val thumbButtonIndex = when {
                    descriptor == prefix -> 1
                    descriptor.startsWith("$prefix ") -> descriptor.removePrefix("$prefix ").toIntOrNull()
                    else -> null
                }

                return if (thumbButtonIndex != null) {
                    val mask = InputEvent.getMaskForButton(thumbButtonIndex + 3)
                    MouseButton.ThumbButton(mask)
                } else null
            }
        }
    }

    private fun translateMouseWheel(descriptor: String): Int? = when (descriptor) {
        "mouse wheel up" -> -1
        "mouse wheel down" -> 1
        else -> null
    }

    private fun translateSpecialKey(descriptor: String): Int? = when (descriptor) {
        "space bar" -> VK_SPACE
        "left shift" -> VK_SHIFT
        "left ctrl" -> VK_CONTROL
        "escape" -> VK_ESCAPE
        "caps lock", "caps lack" -> VK_CAPS_LOCK
        "tab" -> VK_TAB
        "period" -> VK_PERIOD
        "left alt" -> VK_ALT
        "end" -> VK_END
        "down" -> VK_DOWN
        else -> null
    }

    private fun findKeyEventConstant(name: String): Int? = kotlin.runCatching {
        val clazz = KeyEvent::class.java
        val field = clazz.getDeclaredField(name)

        return field.getInt(null)
    }.getOrNull()

    private fun String.isSingleLetterOrNumber() = length == 1 && this[0].isLetterOrDigit()

    private fun String.parseFKey(): Int? = kotlin.runCatching {
        if (this[0] == 'f' && length in 2..3)
            return this.drop(1).toInt()
        return null
    }.getOrNull()

    private fun String.parseNumKey(): Int? = kotlin.runCatching {
        if (this.startsWith("num ") && length == 5)
            return this[4].toString().toInt()
        return null
    }.getOrNull()
}