package logging

import java.util.*

object Profiler {
    private var sectionLabel: Stack<String> = Stack()
    private var sectionStart: Stack<Long> = Stack()

    fun section(label: String) {
        Logger.debug("${Color.GREEN}▲ ${Color.WHITE_BRIGHT}$label")
        sectionLabel.push(label)
        sectionStart.push(System.currentTimeMillis())
    }

    fun endSection() {
        if (!sectionLabel.empty()) {
            val label = sectionLabel.pop()
            val duration = System.currentTimeMillis() - sectionStart.pop()
            Logger.debug("${Color.RED}▼ ${Color.WHITE_BRIGHT}$label ${Color.WHITE}(${Color.YELLOW}${duration}ms${Color.WHITE})")
        }
    }
}

operator fun <T> String.invoke(action: () -> T): T {
    Profiler.section(this)
    val result = action()
    Profiler.endSection()

    return result
}