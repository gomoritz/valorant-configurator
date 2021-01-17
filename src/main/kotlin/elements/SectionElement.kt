package elements

class SectionElement(val name: String, override val height: Int = 66) : Element() {
    override fun toString(): String = name
}