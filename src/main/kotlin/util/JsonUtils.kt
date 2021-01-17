package util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val mapper = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.ALWAYS)
}

fun String.toCamelCase(): String {
    val onlyValidCharacters = replaceMultiple(":", "(", ")", "[", "]", "'", with = "").replaceMultiple("/", "-", with = " ")
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