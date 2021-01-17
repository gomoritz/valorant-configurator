package elements

import elements.KeybindOptionElement.Companion.replaceIllegalCharacters
import interaction.*
import logging.Logger
import ocr.*
import java.awt.event.KeyEvent

const val SINGLE_KEYBIND_VALUE_WIDTH = 468

class SingleKeybindOptionElement(name: String) : OptionElement<String?>(name) {
    override fun readValue(x: Int, y: Int): String? {
        val valueX = x + width - SINGLE_KEYBIND_VALUE_WIDTH

        return takeScreen().capture(valueX, y, SINGLE_KEYBIND_VALUE_WIDTH, height)
            .makeTextReadable().readText()
            .takeUnless { it == "-" }?.toLowerCase()?.replaceIllegalCharacters()
    }

    override fun writeValue(x: Int, y: Int, value: String?) {
        val valueX = x + width - SINGLE_KEYBIND_VALUE_WIDTH

        takeMouse().move(valueX + (SINGLE_KEYBIND_VALUE_WIDTH / 2), y + 15).click()
        if (value != null) {
            if (!KeybindOptionElement.produceInput(value)) Logger.error("Failed to set single keybind for <$name>")
        } else {
            takeKeyboard().type(KeyEvent.VK_BACK_SPACE)
        }
    }
}