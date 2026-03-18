package com.coolappstore.everlastingandroidtweak.features.autoreboot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager

class AutoRebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_REBOOT) {
            try {
                val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                // Requires reboot permission (system/root) or ADB.
                // For Accessibility-based approach, send global action via the service.
                Runtime.getRuntime().exec(arrayOf("su", "-c", "reboot"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    companion object { const val ACTION_REBOOT = "com.coolappstore.everlastingandroidtweak.REBOOT" }
}
