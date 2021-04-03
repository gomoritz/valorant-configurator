package elements

import interaction.*
import logging.Logger
import ocr.*
import interaction.KeybindWriter

const val KEYBIND_VALUE_WIDTH = 232
const val KEYBIND_VALUE_GAP = 4

class KeybindOptionElement(name: String) : OptionElement<Keybind>(name) {
    override fun readValue(x: Int, y: Int): Keybind {
        val valueX = x + width - (KEYBIND_VALUE_WIDTH * 2) - KEYBIND_VALUE_GAP

        val capture = takeScreen().capture(valueX, y, (KEYBIND_VALUE_WIDTH * 2) + KEYBIND_VALUE_GAP, height)
            .makeTextReadable()

        val primary = KeybindReader.readKeybind(capture.getSubimage(0, 0, KEYBIND_VALUE_WIDTH, height))
        val secondary = KeybindReader.readKeybind(capture.getSubimage(KEYBIND_VALUE_WIDTH + KEYBIND_VALUE_GAP, 0, KEYBIND_VALUE_WIDTH, height))

        return Keybind(primary, secondary)
    }

    override fun writeValue(x: Int, y: Int, value: Keybind) {
        val valueX = x + width - (KEYBIND_VALUE_WIDTH * 2) - KEYBIND_VALUE_GAP

        takeMouse().move(valueX + (KEYBIND_VALUE_WIDTH / 2), y + 15).click()
        if (value.primary != null) {
            if (!KeybindWriter.produceInput(value.primary))
                Logger.error("Failed to set primary keybind for <$name>")
        } else {
            takeMouse().move(valueX + KEYBIND_VALUE_WIDTH - 15, y + (height / 2)).click()
        }

        takeMouse().move((valueX + (KEYBIND_VALUE_WIDTH * 1.5) + KEYBIND_VALUE_GAP + 5).toInt(), y + 15).click()
        if (value.secondary != null) {
            if (!KeybindWriter.produceInput(value.secondary))
                Logger.error("Failed to set secondary keybind for <$name>")
        } else {
            takeMouse().move(valueX + (KEYBIND_VALUE_WIDTH * 2) + KEYBIND_VALUE_GAP - 15, y + (height / 2)).click()
        }
    }
}

data class Keybind(val primary: String?, val secondary: String?)