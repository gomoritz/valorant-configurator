package elements

import interaction.takeMouse
import interaction.takeScreen
import logging.Logger
import java.awt.Color
import kotlin.math.pow
import kotlin.math.roundToInt

const val TOGGLE_SLIDER_VALUE_WIDTH = 482
const val TOGGLE_SLIDER_START = 13
const val TOGGLE_SLIDER_WIDTH = 402
const val TOGGLE_SLIDER_Y = 23

class ToggleSliderElement(name: String) : OptionElement<Double>(name) {
    override fun readValue(x: Int, y: Int): Double {
        val valueX = x + width - TOGGLE_SLIDER_VALUE_WIDTH
        val capture = takeScreen().capture(valueX, y, TOGGLE_SLIDER_VALUE_WIDTH, height)

        val toggleColor = Color(capture.getRGB(TOGGLE_SLIDER_VALUE_WIDTH - 27, 23), true)
        val isEnabled = isColorForEnabled(toggleColor)

        if (!isEnabled) {
            return -1.0
        }

        for (scanX in 0..TOGGLE_SLIDER_WIDTH) {
            val color = Color(capture.getRGB(TOGGLE_SLIDER_START + scanX, TOGGLE_SLIDER_Y))
            if (isColorForSliderPosition(color)) {
                return ((scanX + 3.0) / TOGGLE_SLIDER_WIDTH).roundToDecimals(2)
            }
        }

        Logger.error("Could not read toggle slider value for <$name> ")
        return -1.0
    }

    override fun writeValue(x: Int, y: Int, value: Double) {
        val valueX = x + width - TOGGLE_SLIDER_VALUE_WIDTH

        if (value == -1.0) {
            val capture = takeScreen().capture(valueX, y, TOGGLE_SLIDER_VALUE_WIDTH, height)
            val toggleColor = Color(capture.getRGB(TOGGLE_SLIDER_VALUE_WIDTH - 27, 23), true)
            val isEnabled = isColorForEnabled(toggleColor)

            if (isEnabled) {
                takeMouse().move(valueX + TOGGLE_SLIDER_VALUE_WIDTH - 27, y + 23).click()
            }
        } else {
            val targetX = (valueX + TOGGLE_SLIDER_START + (TOGGLE_SLIDER_WIDTH * value)).toInt()
            takeMouse().move(targetX, y + TOGGLE_SLIDER_Y).click()
        }
    }

    companion object {

        fun isColorForEnabled(color: Color) =
            color.red > 110 && color.green > 170 && color.blue > 150

        fun isColorForSliderPosition(color: Color) =
            color.red > 250 && color.green > 250 && color.blue > 250

        fun Double.roundToDecimals(amount: Int): Double {
            val factor = 10.0.pow(amount)
            return (this * factor).roundToInt() / factor
        }
    }
}