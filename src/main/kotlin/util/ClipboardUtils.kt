package util

import logging.Logger
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.DataFlavor

fun setClipboardString(value: String) {
    try {
        val selection = StringSelection(value)
        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(selection, selection)
    } catch (e: Throwable) {
        Logger.warn("Couldn't insert $value into clipboard")
    }
}

fun getClipboardString() = runCatching {
    Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String
}.getOrNull()

fun withClipboard(text: String, action: () -> Unit) {
    val previous = getClipboardString()
    setClipboardString(text)

    action()

    setClipboardString(previous ?: "")
}