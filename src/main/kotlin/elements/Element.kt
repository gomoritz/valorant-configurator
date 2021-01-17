package elements

import kotlin.properties.Delegates

abstract class Element {
    abstract val height: Int
    var width by Delegates.notNull<Int>()

    var shouldSkip: Boolean = false
        private set

    fun skip() = apply { shouldSkip = true }
}