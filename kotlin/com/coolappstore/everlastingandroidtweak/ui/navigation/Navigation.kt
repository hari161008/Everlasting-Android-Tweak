package com.coolappstore.everlastingandroidtweak.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coolappstore.everlastingandroidtweak.ui.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
    object ShakeTorch : Screen("shake_torch")
    object TwistCamera : Screen("twist_camera")
    object TaskManager : Screen("task_manager")
    object Terminal : Screen("terminal")
    object Equalizer : Screen("equalizer")
    object Screensaver : Screen("screensaver")
    object AutoReboot : Screen("auto_reboot")
    object Watermark : Screen("watermark")
    object CacheCleaner : Screen("cache_cleaner")
    object MusicLight : Screen("music_light")
    object Haptics : Screen("haptics")
    object CustomSounds : Screen("custom_sounds")
    object NavBarOverlay : Screen("navbar_overlay")
    object ScreenshotBlocker : Screen("screenshot_blocker")
    object VolumeStyles : Screen("volume_styles")
    object HiddenFeatures : Screen("hidden_features")
    object WallpaperEffects : Screen("wallpaper_effects")
}

@Composable
fun EverlastingNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.ShakeTorch.route) { ShakeTorchScreen(navController) }
        composable(Screen.TwistCamera.route) { TwistCameraScreen(navController) }
        composable(Screen.TaskManager.route) { TaskManagerScreen(navController) }
        composable(Screen.Terminal.route) { TerminalScreen(navController) }
        composable(Screen.Equalizer.route) { EqualizerScreen(navController) }
        composable(Screen.Screensaver.route) { ScreensaverScreen(navController) }
        composable(Screen.AutoReboot.route) { AutoRebootScreen(navController) }
        composable(Screen.Watermark.route) { WatermarkScreen(navController) }
        composable(Screen.CacheCleaner.route) { CacheCleanerScreen(navController) }
        composable(Screen.MusicLight.route) { MusicLightScreen(navController) }
        composable(Screen.Haptics.route) { HapticsScreen(navController) }
        composable(Screen.CustomSounds.route) { CustomSoundsScreen(navController) }
        composable(Screen.NavBarOverlay.route) { NavBarOverlayScreen(navController) }
        composable(Screen.ScreenshotBlocker.route) { ScreenshotBlockerScreen(navController) }
        composable(Screen.VolumeStyles.route) { VolumeStylesScreen(navController) }
        composable(Screen.HiddenFeatures.route) { HiddenFeaturesScreen(navController) }
        composable(Screen.WallpaperEffects.route) { WallpaperEffectsScreen(navController) }
    }
}
