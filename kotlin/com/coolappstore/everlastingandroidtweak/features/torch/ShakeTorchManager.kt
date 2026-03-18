package com.coolappstore.everlastingandroidtweak.features.torch

import android.content.Context
import android.hardware.Camera
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import kotlin.math.sqrt

class ShakeTorchManager(private val context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var lastShakeTime = 0L
    private var torchOn = false
    private var sensitivity = 12f // m/s^2 threshold
    private var onShakeCallback: (() -> Unit)? = null

    var isRunning = false
        private set

    fun setSensitivity(value: Float) { sensitivity = value }

    fun setOnShakeCallback(callback: () -> Unit) { onShakeCallback = callback }

    fun start() {
        if (isRunning) return
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        isRunning = true
    }

    fun stop() {
        sensorManager.unregisterListener(this)
        isRunning = false
        // Ensure torch is off when stopping
        if (torchOn) toggleTorch()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val deltaX = x - lastX
        val deltaY = y - lastY
        val deltaZ = z - lastZ
        lastX = x; lastY = y; lastZ = z

        val acceleration = sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)

        val now = System.currentTimeMillis()
        if (acceleration > sensitivity && now - lastShakeTime > 800L) {
            lastShakeTime = now
            toggleTorch()
            onShakeCallback?.invoke()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    private fun toggleTorch() {
        try {
            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                cameraManager.getCameraCharacteristics(id)
                    .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            } ?: return
            torchOn = !torchOn
            cameraManager.setTorchMode(cameraId, torchOn)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isTorchOn() = torchOn
}
