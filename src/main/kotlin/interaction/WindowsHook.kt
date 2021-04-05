package interaction

import com.sun.jna.platform.win32.User32

object WindowsHook {
    fun focusValorant(): Boolean {
        return try {
            val hwnd = User32.INSTANCE.FindWindow("UnrealWindow", "VALORANT  ")
            User32.INSTANCE.SetForegroundWindow(hwnd)
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }
    }
}