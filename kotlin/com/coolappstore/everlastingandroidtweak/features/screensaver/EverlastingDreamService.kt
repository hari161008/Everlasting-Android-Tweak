package com.coolappstore.everlastingandroidtweak.features.screensaver

import android.graphics.Color
import android.service.dreams.DreamService
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextClock

class EverlastingDreamService : DreamService() {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isInteractive = false
        isFullscreen = true

        val root = FrameLayout(this)
        root.setBackgroundColor(Color.BLACK)

        val clock = TextClock(this).apply {
            format12Hour = "hh:mm"
            format24Hour = "HH:mm"
            textSize = 80f
            setTextColor(Color.WHITE)
        }

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply { gravity = Gravity.CENTER }

        root.addView(clock, params)
        setContentView(root)
    }
}
