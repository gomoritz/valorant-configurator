package elements

import elements.KeybindOptionElement.Companion.replaceIllegalCharacters
import interaction.*
import logging.Logger
import ocr.*
import java.awt.event.KeyEvent

class SingleKeybindOptionElement(name: String) : OptionElement<String?>(name) {
    override fun readValue(x: Int, y: Int): String? {
        val valueX = x + width - valueWidth

        return takeScreen().capture(valueX, y, valueWidth, height)
            .makeTextReadable().readText()
            .takeUnless { it == "-" }?.toLowerCase()?.replaceIllegalCharacters()
    }

    override fun writeValue(x: Int, y: Int, value: String?) {
        val valueX = x + width - valueWidth

        takeMouse().move(valueX + (valueWidth / 2), y + (height / 2)).click()
        if (value != null) {
            if (!KeybindOptionElement.produceInput(value)) Logger.error("Failed to set single keybind for <$name>")
        } else {
            takeKeyboard().type(KeyEvent.VK_BACK_SPACE)
        }
    }
}