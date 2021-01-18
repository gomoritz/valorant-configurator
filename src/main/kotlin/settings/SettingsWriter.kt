package settings

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.treeToValue
import elements.*
import logging.Logger
import logging.invoke
import util.mapper
import util.toCamelCase
import java.io.File
import java.lang.Exception

fun writeSettings(file: File) = "Write settings" {
    val objectNode = mapper.readTree(file) as ObjectNode

    var categoryObject: ObjectNode? = null
    var tabObject: ObjectNode? = null
    var sectionObject: ObjectNode? = null

    SettingsTraverser { element, x, y ->
        try {
            val target = (sectionObject ?: tabObject) ?: categoryObject!!
            val value = target.get(element.name.toCamelCase())

            when (element) {
                is SliderOptionElement -> element.writeValue(x, y, value.doubleValue())
                is DropdownOptionElement -> element.writeValue(x, y, value.intValue())
                is SwitchOptionElement -> element.writeValue(x, y, value.booleanValue())
                is TripleOptionElement -> element.writeValue(x, y, value.intValue())
                is QuadrupleOptionElement -> element.writeValue(x, y, value.intValue())
                is KeybindOptionElement -> element.writeValue(x, y, mapper.treeToValue<Keybind>(value)!!)
                is SingleKeybindOptionElement -> element.writeValue(x, y, value.takeIf { it?.isNull == false }?.textValue())
                is FieldOptionElement -> element.writeValue(x, y, value.doubleValue())
                is ToggleSliderElement -> element.writeValue(x, y, value.doubleValue())
                is CheckboxElement -> element.writeValue(x, y, value.booleanValue())
            }
        } catch (e: Exception) {
            Logger.error("Failed to apply value to setting <${element.name}>:")
            Logger.error(e.stackTraceToString())
        }
    }.handleCategory { category ->
        categoryObject = objectNode.get(category.name.toCamelCase()) as ObjectNode
        tabObject = null
        sectionObject = null
    }.handleTab { tab ->
        tabObject = categoryObject!!.get(tab.name.toCamelCase()) as ObjectNode
        sectionObject = null
    }.handleSection { section ->
        sectionObject = (tabObject ?: categoryObject!!).get(section.name.toCamelCase()) as ObjectNode
    }.start()
}