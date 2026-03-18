package com.coolappstore.everlastingandroidtweak.features.musiclight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.os.*
import kotlinx.coroutines.*
import kotlin.math.*

/**
 * Music Reactive Light & Vibration
 * Listens to audio levels and pulses torch/vibration in sync.
 * Uses AudioRecord to monitor microphone amplitude as a proxy for music playback.
 * For supported devices (Pixel, Motorola), uses torch dimming via TorchStrengthLevel API (Android 13+).
 */
class MusicLightManager(private val context: Context) {

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
    else @Suppress("DEPRECATION") context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    private var job: Job? = null
    private var torchCameraId: String? = null
    private var lightEnabled = true
    private var vibrationEnabled = true
    private var isRunning = false
    private var maxTorchLevel = 1

    init {
        // Find camera with flash
        torchCameraId = cameraManager.cameraIdList.firstOrNull { id ->
            cameraManager.getCameraCharacteristics(id)
                .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
        }
        // Get max torch strength level (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            torchCameraId?.let { id ->
                maxTorchLevel = cameraManager.getCameraCharacteristics(id)
                    .get(CameraCharacteristics.FLASH_INFO_STRENGTH_MAXIMUM_LEVEL) ?: 1
            }
        }
    }

    fun setLightEnabled(enabled: Boolean) { lightEnabled = enabled }
    fun setVibrationEnabled(enabled: Boolean) { vibrationEnabled = enabled }

    fun start() {
        if (isRunning) return
        isRunning = true
        job = CoroutineScope(Dispatchers.IO).launch {
            runMusicReactiveLoop()
        }
    }

    fun stop() {
        isRunning = false
        job?.cancel()
        job = null
        // Turn off torch
        try {
            torchCameraId?.let { cameraManager.setTorchMode(it, false) }
        } catch (_: Exception) {}
    }

    private suspend fun runMusicReactiveLoop() {
        // Use AudioRecord to sample microphone/media audio amplitude
        val sampleRate = 44100
        val bufferSize = android.media.AudioRecord.getMinBufferSize(
            sampleRate,
            android.media.AudioFormat.CHANNEL_IN_MONO,
            android.media.AudioFormat.ENCODING_PCM_16BIT
        )

        val audioRecord = try {
            android.media.AudioRecord(
                android.media.MediaRecorder.AudioSource.VOICE_RECOGNITION,
                sampleRate,
                android.media.AudioFormat.CHANNEL_IN_MONO,
                android.media.AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )
        } catch (e: SecurityException) {
            return  // No RECORD_AUDIO permission
        }

        if (audioRecord.state != android.media.AudioRecord.STATE_INITIALIZED) return

        audioRecord.startRecording()
        val buffer = ShortArray(bufferSize / 2)
        var smoothedAmplitude = 0f

        try {
            while (isRunning && currentCoroutineContext().isActive) {
                val read = audioRecord.read(buffer, 0, buffer.size)
                if (read > 0) {
                    // Calculate RMS amplitude
                    var sum = 0.0
                    for (i in 0 until read) sum += buffer[i] * buffer[i]
                    val rms = sqrt(sum / read).toFloat()

                    // Smooth
                    smoothedAmplitude = smoothedAmplitude * 0.7f + rms * 0.3f

                    // Normalize to 0..1 (typical speech/music ~100-5000 RMS)
                    val normalizedLevel = (smoothedAmplitude / 4000f).coerceIn(0f, 1f)

                    withContext(Dispatchers.Main) {
                        applyReaction(normalizedLevel)
                    }
                }
                delay(50) // ~20fps
            }
        } finally {
            audioRecord.stop()
            audioRecord.release()
            withContext(Dispatchers.Main) {
                try { torchCameraId?.let { cameraManager.setTorchMode(it, false) } } catch (_: Exception) {}
            }
        }
    }

    private fun applyReaction(level: Float) {
        // Torch dimming
        if (lightEnabled) {
            torchCameraId?.let { id ->
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && maxTorchLevel > 1) {
                        // Smooth dimming on supported devices
                        val strengthLevel = (level * maxTorchLevel).toInt().coerceAtLeast(0)
                        if (strengthLevel == 0) {
                            cameraManager.setTorchMode(id, false)
                        } else {
                            cameraManager.turnOnTorchWithStrengthLevel(id, strengthLevel)
                        }
                    } else {
                        // Binary on/off for devices without dimming
                        cameraManager.setTorchMode(id, level > 0.15f)
                    }
                } catch (_: Exception) {}
            }
        }

        // Vibration pulse
        if (vibrationEnabled && level > 0.3f) {
            val amplitude = (level * 200).toInt().coerceIn(10, 200)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(30, amplitude))
            } else {
                @Suppress("DEPRECATION") vibrator.vibrate(30)
            }
        }
    }
}
