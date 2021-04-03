package interaction

import logging.Logger
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import java.util.logging.Level
import kotlin.system.exitProcess
import java.util.logging.Logger as JavaLogger

object EmergencyBrake : NativeKeyListener {
    fun init() {
        val logger: JavaLogger = JavaLogger.getLogger(GlobalScreen::class.java.getPackage().name)
        logger.level = Level.OFF

        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(this)
    }

    override fun nativeKeyTyped(event: NativeKeyEvent) {
    }

    override fun nativeKeyPressed(event: NativeKeyEvent) {
        // shortcut Ctrl + S
        if (event.keyCode == 31 && event.modifiers == 2) {
            Logger.error("Emergency brake pulled, <terminating application>...")
            exitProcess(2)
        }
    }

    override fun nativeKeyReleased(event: NativeKeyEvent) {
    }
}