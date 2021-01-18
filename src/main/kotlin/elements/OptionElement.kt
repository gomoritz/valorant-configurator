package elements

abstract class OptionElement<T>(name: String) : Element(name) {
    override var height: Int = 52

    abstract fun readValue(x: Int, y: Int): T

    abstract fun writeValue(x: Int, y: Int, value: T)

    override fun toString() = name
}