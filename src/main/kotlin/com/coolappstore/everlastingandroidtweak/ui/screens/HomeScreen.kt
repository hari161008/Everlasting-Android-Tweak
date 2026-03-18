package com.coolappstore.everlastingandroidtweak.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar
import com.coolappstore.everlastingandroidtweak.ui.components.FeatureCard
import com.coolappstore.everlastingandroidtweak.ui.components.SectionHeader
import com.coolappstore.everlastingandroidtweak.ui.navigation.Screen

data class FeatureItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val route: String,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val features = listOf(
        // Gestures
        FeatureItem("Shake for Torch", "Shake your phone to toggle flashlight", Icons.Default.FlashOn, Screen.ShakeTorch.route, "Gestures"),
        FeatureItem("Twist for Camera", "Twist wrist gesture to open camera", Icons.Default.CameraAlt, Screen.TwistCamera.route, "Gestures"),
        FeatureItem("Custom Nav Bar", "Overlay custom gesture/button navigation", Icons.Default.Navigation, Screen.NavBarOverlay.route, "Gestures"),

        // Audio & Haptics
        FeatureItem("Built-in Equalizer", "Tune audio with 5-band EQ", Icons.Default.Equalizer, Screen.Equalizer.route, "Audio & Haptics"),
        FeatureItem("Custom Haptics", "Customize tap & scroll vibration", Icons.Default.Vibration, Screen.Haptics.route, "Audio & Haptics"),
        FeatureItem("Custom Sounds", "Lock, unlock & tap sounds", Icons.Default.VolumeUp, Screen.CustomSounds.route, "Audio & Haptics"),
        FeatureItem("Volume Styles", "Visual styles for volume panel", Icons.Default.VolumeUp, Screen.VolumeStyles.route, "Audio & Haptics"),

        // Music & Light
        FeatureItem("Music Reactive Light", "Flash & vibrate to music beat (Nothing Glyph-style)", Icons.Default.MusicNote, Screen.MusicLight.route, "Music & Light"),

        // Device & System
        FeatureItem("Task Manager", "View & kill running processes", Icons.Default.Memory, Screen.TaskManager.route, "Device & System"),
        FeatureItem("Terminal", "Run shell commands on your device", Icons.Default.Terminal, Screen.Terminal.route, "Device & System"),
        FeatureItem("Cache Cleaner", "Clear app caches via Shizuku", Icons.Default.CleaningServices, Screen.CacheCleaner.route, "Device & System"),
        FeatureItem("Auto Reboot", "Schedule automatic reboots", Icons.Default.RestartAlt, Screen.AutoReboot.route, "Device & System"),
        FeatureItem("Hidden Features", "Unlock hidden Android settings", Icons.Default.Lock, Screen.HiddenFeatures.route, "Device & System"),

        // Visuals
        FeatureItem("Screensaver", "Beautiful screensaver themes", Icons.Default.Slideshow, Screen.Screensaver.route, "Visuals"),
        FeatureItem("Wallpaper Effects", "Pixel-style wallpaper effects", Icons.Default.Wallpaper, Screen.WallpaperEffects.route, "Visuals"),
        FeatureItem("Screenshot Blocker", "Block screenshots in sensitive apps", Icons.Default.Screenshot, Screen.ScreenshotBlocker.route, "Visuals"),

        // Camera & Media
        FeatureItem("Auto Watermark", "Auto-watermark photos when taken", Icons.Default.WaterDrop, Screen.Watermark.route, "Camera & Media"),
    )

    val categories = features.map { it.category }.distinct()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column {
                            Text("Everlasting Tweak", fontWeight = FontWeight.Bold)
                            Text(
                                "Android Enhancement Suite",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                            Icon(Icons.Default.Settings, "Settings")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                item { SectionHeader(title = category, modifier = Modifier.padding(top = 8.dp)) }
                items(features.filter { it.category == category }) { feature ->
                    FeatureCard(
                        title = feature.title,
                        subtitle = feature.subtitle,
                        icon = feature.icon,
                        onClick = { navController.navigate(feature.route) }
                    )
                }
            }
        }
    }
}
