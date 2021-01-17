package settings

import com.fasterxml.jackson.databind.node.ObjectNode
import elements.*
import logging.invoke
import util.mapper
import util.toCamelCase
import java.io.File

fun readSettings(file: File) = "Read general settings" {
    val objectNode = mapper.createObjectNode()

    var categoryObject: ObjectNode? = null
    var tabObject: ObjectNode? = null
    var sectionObject: ObjectNode? = null

    SettingsTraverser { element, x, y ->
        val key = element.name.toCamelCase()
        val target = (sectionObject ?: tabObject) ?: categoryObject!!

        when (val value = element.readValue(x, y)) {
            is Double -> target.put(key, value)
            is Int -> target.put(key, value)
            is Boolean -> target.put(key, value)
            is String -> target.put(key, value)
            is Keybind -> target.putPOJO(key, value)
        }
    }.handleCategory { category ->
        categoryObject = mapper.createObjectNode()
        tabObject = null
        sectionObject = null

        objectNode.set<ObjectNode>(category.name.toCamelCase(), categoryObject)
    }.handleTab { tab ->
        tabObject = mapper.createObjectNode()
        sectionObject = null

        categoryObject!!.set<ObjectNode>(tab.name.toCamelCase(), tabObject)
    }.handleSection { section ->
        sectionObject = mapper.createObjectNode()

        (tabObject ?: categoryObject!!).set<ObjectNode>(section.name.toCamelCase(), sectionObject)
    }.start()

    mapper.writeValue(file, objectNode)
}