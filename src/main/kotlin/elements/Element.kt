package elements

import kotlin.properties.Delegates

abstract class Element(val name: String) {
    abstract var height: Int
    var width by Delegates.notNull<Int>()
    var valueWidth by Delegates.notNull<Int>()

    var shouldSkip: Boolean = false
        private set

    fun skip() = apply { shouldSkip = true }
}