package com.coolappstore.everlastingandroidtweak.features.watermark

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import kotlinx.coroutines.*
import java.io.File

class WatermarkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val uri = intent.data ?: return
        val path = uri.path ?: return
        if (!path.endsWith(".jpg", true) && !path.endsWith(".jpeg", true) &&
            !path.endsWith(".png", true)) return

        CoroutineScope(Dispatchers.IO).launch {
            applyWatermark(context, path, "© My Photo")
        }
    }

    private fun applyWatermark(context: Context, path: String, text: String) {
        try {
            val file = File(path)
            if (!file.exists()) return

            val original = BitmapFactory.decodeFile(path) ?: return
            val mutable = original.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(mutable)

            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                alpha = 180
                textSize = mutable.width * 0.04f
                setShadowLayer(4f, 2f, 2f, Color.BLACK)
            }

            val x = mutable.width - paint.measureText(text) - 40f
            val y = mutable.height - 50f
            canvas.drawText(text, x, y, paint)

            file.outputStream().use { out ->
                mutable.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }

            original.recycle()
            mutable.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
