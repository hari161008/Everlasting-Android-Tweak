package com.coolappstore.everlastingandroidtweak

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.coolappstore.everlastingandroidtweak.data.AppPreferences

class EverlastingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        AppPreferences.init(this)
    }

    private fun createNotificationChannels() {
        val nm = getSystemService(NotificationManager::class.java)

        nm.createNotificationChannel(
            NotificationChannel(
                CHANNEL_FOREGROUND,
                "Background Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply { description = "Keeps gesture/haptic features running" }
        )

        nm.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ALERTS,
                "Alerts",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "App alerts and notifications" }
        )
    }

    companion object {
        const val CHANNEL_FOREGROUND = "foreground_service"
        const val CHANNEL_ALERTS = "alerts"
    }
}
