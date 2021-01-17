package elements

abstract class OptionElement<T>(val name: String) : Element() {
    override val height: Int = 52

    abstract fun readValue(x: Int, y: Int): T

    abstract fun writeValue(x: Int, y: Int, value: T)

    override fun toString() = name
}