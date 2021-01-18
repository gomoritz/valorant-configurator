package elements

import interaction.takeMouse
import interaction.takeScreen
import logging.Logger
import ocr.debugFile
import java.awt.Color
import kotlin.math.pow
import kotlin.math.roundToInt

const val UNLABELLED_SLIDER_START = 13
const val UNLABELLED_SLIDER_Y = 23

class UnlabelledSliderOptionElement(name: String, val canToggle: Boolean = true) : OptionElement<Double>(name) {

    val sliderWidth = if (canToggle) 402 else 455

    override fun readValue(x: Int, y: Int): Double {
        val valueX = x + width - valueWidth
        val capture = takeScreen().capture(valueX, y, valueWidth, height)

        if (canToggle) {
            val toggleColor = Color(capture.getRGB(valueWidth - 27, 23), true)
            val isEnabled = isColorForEnabled(toggleColor)

            if (!isEnabled) {
                return -1.0
            }
        }

        for (scanX in 0..sliderWidth) {
            val color = Color(capture.getRGB(UNLABELLED_SLIDER_START + scanX, UNLABELLED_SLIDER_Y))
            if (isColorForSliderPosition(color)) {
                return ((scanX + 3.0) / sliderWidth).roundToDecimals(2)
            }
        }

        Logger.error("Could not read unlabelled slider value for <$name> ")
        return -1.0
    }

    override fun writeValue(x: Int, y: Int, value: Double) {
        val valueX = x + width - valueWidth

        if (value == -1.0) {
            if (canToggle) {
                val capture = takeScreen().capture(valueX, y, valueWidth, height)
                val toggleColor = Color(capture.getRGB(valueWidth - 27, 23), true)
                val isEnabled = isColorForEnabled(toggleColor)

                if (isEnabled) {
                    takeMouse().move(valueX + valueWidth - 27, y + 23).click()
                }
            }
        } else {
            val targetX = (valueX + UNLABELLED_SLIDER_START + (sliderWidth * value)).toInt()
            takeMouse().move(targetX, y + UNLABELLED_SLIDER_Y).click()
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