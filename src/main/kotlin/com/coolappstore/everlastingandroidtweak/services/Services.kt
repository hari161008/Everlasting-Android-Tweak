package com.coolappstore.everlastingandroidtweak.services

import android.app.*
import android.content.*
import android.os.*
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import com.coolappstore.everlastingandroidtweak.EverlastingApp
import com.coolappstore.everlastingandroidtweak.R
import com.coolappstore.everlastingandroidtweak.features.torch.ShakeTorchManager
import com.coolappstore.everlastingandroidtweak.features.camera.TwistCameraManager

// ─── FOREGROUND SERVICE ─────────────────────────────────────────────────────
class EverlastingForegroundService : Service() {

    private lateinit var shakeTorchManager: ShakeTorchManager
    private lateinit var twistCameraManager: TwistCameraManager

    override fun onCreate() {
        super.onCreate()
        shakeTorchManager = ShakeTorchManager(this)
        twistCameraManager = TwistCameraManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIF_ID, buildNotification())

        shakeTorchManager.start()
        twistCameraManager.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        shakeTorchManager.stop()
        twistCameraManager.stop()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(): Notification =
        NotificationCompat.Builder(this, EverlastingApp.CHANNEL_FOREGROUND)
            .setContentTitle("Everlasting Tweak Active")
            .setContentText("Gesture detection running")
            .setSmallIcon(android.R.drawable.ic_menu_manage)
            .setOngoing(true)
            .build()

    companion object {
        private const val NOTIF_ID = 1001
        fun start(context: Context) {
            val intent = Intent(context, EverlastingForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context.startForegroundService(intent)
            else context.startService(intent)
        }
        fun stop(context: Context) {
            context.stopService(Intent(context, EverlastingForegroundService::class.java))
        }
    }
}

// ─── NOTIFICATION LISTENER ──────────────────────────────────────────────────
class EverlastingNotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {}
    override fun onNotificationRemoved(sbn: StatusBarNotification) {}
}

// ─── BOOT RECEIVER ──────────────────────────────────────────────────────────
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_LOCKED_BOOT_COMPLETED) {
            EverlastingForegroundService.start(context)
        }
    }
}
