package settings

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import elements.*
import logging.invoke
import java.io.File

private val mapper = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.ALWAYS)
}

fun readGeneralSettings(file: File) = "Read general settings" {
    val objectNode = mapper.createObjectNode()

    SettingsTraverser { element, x, y ->
        val key = element.name.toCamelCase()
        when (val value = element.readValue(x, y)) {
            is Double -> objectNode.put(key, value)
            is Int -> objectNode.put(key, value)
            is Boolean -> objectNode.put(key, value)
            is String -> objectNode.put(key, value)
            is Keybind -> objectNode.putPOJO(key, value)
        }
    }.start()

    mapper.writeValue(file, objectNode)
}

fun writeGeneralSettings(file: File) = "Write general settings" {
    val objectNode = mapper.readTree(file) as ObjectNode

    SettingsTraverser { element, x, y ->
        val value = objectNode.get(element.name.toCamelCase())
        when (element) {
            is SliderOptionElement -> element.writeValue(x, y, value.doubleValue())
            is DropdownOptionElement -> element.writeValue(x, y, value.intValue())
            is SwitchOptionElement -> element.writeValue(x, y, value.booleanValue())
            is TripleOptionElement -> element.writeValue(x, y, value.intValue())
            is KeybindOptionElement -> element.writeValue(x, y, mapper.treeToValue<Keybind>(value)!!)
            is SingleKeybindOptionElement -> element.writeValue(x, y, value.takeIf { it?.isNull == false }?.textValue())
        }
    }.start()
}

private fun String.toCamelCase(): String {
    val onlyValidCharacters = replaceMultiple(":", "(", ")", "[", "]", with = "").replaceMultiple("/", "-", with = " ")
    val charArray = onlyValidCharacters.toLowerCase().toCharArray()

    for ((index, char) in charArray.withIndex()) {
        if (char == ' ') {
            charArray[index + 1] = charArray[index + 1].toUpperCase()
        }
    }

    return String(charArray).replace(" ", "")
}

private fun String.replaceMultiple(vararg values: String, with: String): String {
    var result = this
    for (oldValue in values) result = result.replace(oldValue, with)
    return result
}