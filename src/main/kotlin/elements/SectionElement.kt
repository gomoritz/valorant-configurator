package elements

class SectionElement(name: String, override var height: Int = 66) : Element(name) {
    override fun toString(): String = name
}