package com.coolappstore.everlastingandroidtweak.features.camera

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

class TwistCameraManager(private val context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var lastTwistTime = 0L
    private var twistCount = 0
    private var isRunning = false
    private var onTwistCallback: (() -> Unit)? = null

    // Twist detection: 2 quick wrist rotations around Z-axis
    private val TWIST_THRESHOLD = 3.5f  // rad/s
    private val TWIST_WINDOW_MS = 700L
    private val REQUIRED_TWISTS = 2

    fun setOnTwistCallback(cb: () -> Unit) { onTwistCallback = cb }

    fun start() {
        if (isRunning) return
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
            isRunning = true
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
        isRunning = false
        twistCount = 0
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_GYROSCOPE) return

        val rotZ = event.values[2]  // Z-axis rotation (wrist twist)
        val now = System.currentTimeMillis()

        if (abs(rotZ) > TWIST_THRESHOLD) {
            if (now - lastTwistTime > TWIST_WINDOW_MS) {
                // Reset if too much time passed
                twistCount = 1
            } else {
                twistCount++
            }
            lastTwistTime = now

            if (twistCount >= REQUIRED_TWISTS) {
                twistCount = 0
                triggerCamera()
                onTwistCallback?.invoke()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    private fun triggerCamera() {
        try {
            val intent = Intent("android.media.action.STILL_IMAGE_CAMERA").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
