package com.coolappstore.everlastingandroidtweak.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "everlasting_prefs")

object AppPreferences {
    private lateinit var context: Context

    fun init(ctx: Context) { context = ctx.applicationContext }

    // Keys
    val SHAKE_TORCH_ENABLED = booleanPreferencesKey("shake_torch_enabled")
    val SHAKE_SENSITIVITY = floatPreferencesKey("shake_sensitivity")
    val TWIST_CAMERA_ENABLED = booleanPreferencesKey("twist_camera_enabled")
    val CUSTOM_HAPTICS_ENABLED = booleanPreferencesKey("custom_haptics_enabled")
    val HAPTICS_INTENSITY = intPreferencesKey("haptics_intensity")
    val HAPTICS_SCROLL_ENABLED = booleanPreferencesKey("haptics_scroll_enabled")
    val TAP_SOUND_ENABLED = booleanPreferencesKey("tap_sound_enabled")
    val TAP_SOUND_URI = stringPreferencesKey("tap_sound_uri")
    val LOCK_SOUND_ENABLED = booleanPreferencesKey("lock_sound_enabled")
    val UNLOCK_SOUND_ENABLED = booleanPreferencesKey("unlock_sound_enabled")
    val LOCK_SOUND_URI = stringPreferencesKey("lock_sound_uri")
    val UNLOCK_SOUND_URI = stringPreferencesKey("unlock_sound_uri")
    val MUSIC_LIGHT_ENABLED = booleanPreferencesKey("music_light_enabled")
    val MUSIC_VIBRATE_ENABLED = booleanPreferencesKey("music_vibrate_enabled")
    val WATERMARK_ENABLED = booleanPreferencesKey("watermark_enabled")
    val WATERMARK_TEXT = stringPreferencesKey("watermark_text")
    val WATERMARK_POSITION = stringPreferencesKey("watermark_position")
    val AUTO_REBOOT_ENABLED = booleanPreferencesKey("auto_reboot_enabled")
    val AUTO_REBOOT_TIME = stringPreferencesKey("auto_reboot_time")
    val AUTO_REBOOT_DAYS = stringPreferencesKey("auto_reboot_days")
    val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
    val DARK_THEME = intPreferencesKey("dark_theme") // 0=system, 1=light, 2=dark
    val SCREENSHOT_BLOCK_ENABLED = booleanPreferencesKey("screenshot_block_enabled")
    val NAVBAR_OVERLAY_ENABLED = booleanPreferencesKey("navbar_overlay_enabled")
    val NAVBAR_STYLE = stringPreferencesKey("navbar_style")
    val VOLUME_STYLE = stringPreferencesKey("volume_style")
    val SCREENSAVER_THEME = stringPreferencesKey("screensaver_theme")
    val EQUALIZER_ENABLED = booleanPreferencesKey("equalizer_enabled")
    val EQ_BAND_LEVELS = stringPreferencesKey("eq_band_levels")

    fun <T> get(key: Preferences.Key<T>, default: T): Flow<T> =
        context.dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { it[key] ?: default }

    suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { it[key] = value }
    }
}
