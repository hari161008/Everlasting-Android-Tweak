package com.coolappstore.everlastingandroidtweak.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.data.AppPreferences
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar
import com.coolappstore.everlastingandroidtweak.ui.components.SectionHeader
import com.coolappstore.everlastingandroidtweak.ui.components.ToggleSettingRow
import kotlinx.coroutines.launch

// ─── SCREENSAVER ────────────────────────────────────────────────────────────
@Composable
fun ScreensaverScreen(navController: NavController) {
    val themes = listOf("Clock", "Photo Slideshow", "Colors", "Nature", "Abstract")
    var selected by remember { mutableStateOf(0) }

    Scaffold(topBar = { EverlastingTopBar("Screensaver", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            SectionHeader("Theme")
            themes.forEachIndexed { i, theme ->
                ListItem(
                    headlineContent = { Text(theme) },
                    leadingContent = { RadioButton(selected = selected == i, onClick = { selected = i }) }
                )
                HorizontalDivider()
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) { Text("Preview Screensaver") }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) { Text("Open Screensaver Settings") }
        }
    }
}

// ─── AUTO REBOOT ────────────────────────────────────────────────────────────
@Composable
fun AutoRebootScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val enabled by AppPreferences.get(AppPreferences.AUTO_REBOOT_ENABLED, false).collectAsState(false)
    var hour by remember { mutableIntStateOf(3) }
    var minute by remember { mutableIntStateOf(0) }
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val selectedDays = remember { mutableStateListOf(false, false, false, false, false, false, false) }

    Scaffold(topBar = { EverlastingTopBar("Auto Reboot", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            ToggleSettingRow(
                "Enable Auto Reboot",
                "Automatically reboot at the scheduled time",
                enabled,
                { scope.launch { AppPreferences.set(AppPreferences.AUTO_REBOOT_ENABLED, it) } }
            )
            HorizontalDivider()
            SectionHeader("Reboot Time")
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = hour.toString().padStart(2, '0'),
                    onValueChange = { it.toIntOrNull()?.let { v -> if (v in 0..23) hour = v } },
                    label = { Text("Hour") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Text(":", style = MaterialTheme.typography.headlineMedium)
                OutlinedTextField(
                    value = minute.toString().padStart(2, '0'),
                    onValueChange = { it.toIntOrNull()?.let { v -> if (v in 0..59) minute = v } },
                    label = { Text("Minute") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            SectionHeader("Repeat Days")
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                days.forEachIndexed { i, d ->
                    FilterChip(
                        selected = selectedDays[i],
                        onClick = { selectedDays[i] = !selectedDays[i] },
                        label = { Text(d, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                enabled = enabled
            ) { Text("Save Schedule") }
        }
    }
}

// ─── WATERMARK ──────────────────────────────────────────────────────────────
@Composable
fun WatermarkScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val enabled by AppPreferences.get(AppPreferences.WATERMARK_ENABLED, false).collectAsState(false)
    var watermarkText by remember { mutableStateOf("© My Photo") }
    val positions = listOf("Bottom Right", "Bottom Left", "Top Right", "Top Left", "Center")
    var selectedPos by remember { mutableStateOf(0) }

    Scaffold(topBar = { EverlastingTopBar("Auto Watermark", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            ToggleSettingRow(
                "Enable Auto Watermark",
                "Add watermark to photos automatically when taken",
                enabled,
                { scope.launch { AppPreferences.set(AppPreferences.WATERMARK_ENABLED, it) } }
            )
            HorizontalDivider()
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = watermarkText,
                    onValueChange = { watermarkText = it },
                    label = { Text("Watermark Text") },
                    modifier = Modifier.fillMaxWidth()
                )
                SectionHeader("Position")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    positions.forEachIndexed { i, pos ->
                        FilterChip(selected = selectedPos == i, onClick = { selectedPos = i },
                            label = { Text(pos, style = MaterialTheme.typography.labelSmall) })
                    }
                }
            }
            Card(
                Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("Uses FileObserver to detect new photos, then overlays your text automatically.",
                    Modifier.padding(16.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ─── CACHE CLEANER ──────────────────────────────────────────────────────────
@Composable
fun CacheCleanerScreen(navController: NavController) {
    var shizukuAvailable by remember { mutableStateOf(false) }
    var cleaning by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }

    Scaffold(topBar = { EverlastingTopBar("Cache Cleaner", navController) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {

            Card(colors = CardDefaults.cardColors(
                containerColor = if (shizukuAvailable) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.errorContainer
            )) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(if (shizukuAvailable) Icons.Default.CheckCircle else Icons.Default.Warning, null)
                    Column {
                        Text(if (shizukuAvailable) "Shizuku is Active" else "Shizuku Required")
                        Text("Install Shizuku from Play Store to clear caches without root",
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Button(
                onClick = { cleaning = true; result = "Clearing caches..." },
                modifier = Modifier.fillMaxWidth(),
                enabled = shizukuAvailable && !cleaning
            ) {
                if (cleaning) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                }
                Text("Clear All App Caches")
            }

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) { Text("Get Shizuku from Play Store") }

            if (result.isNotEmpty()) {
                Card { Text(result, Modifier.padding(16.dp)) }
            }
        }
    }
}

// ─── MUSIC LIGHT ────────────────────────────────────────────────────────────
@Composable
fun MusicLightScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val lightEnabled by AppPreferences.get(AppPreferences.MUSIC_LIGHT_ENABLED, false).collectAsState(false)
    val vibrateEnabled by AppPreferences.get(AppPreferences.MUSIC_VIBRATE_ENABLED, false).collectAsState(false)
    var sensitivity by remember { mutableFloatStateOf(0.5f) }

    Scaffold(topBar = { EverlastingTopBar("Music Reactive Light", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            Card(
                Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("🎵 Nothing Glyph-style", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text("Flashlight and/or vibration pulse in sync with music. On Pixel/Motorola devices with torch dimming support, the light smoothly dims/glows with the beat.",
                        style = MaterialTheme.typography.bodySmall)
                }
            }
            ToggleSettingRow("Flash with Music", "Pulse torch to the music beat", lightEnabled,
                { scope.launch { AppPreferences.set(AppPreferences.MUSIC_LIGHT_ENABLED, it) } })
            HorizontalDivider()
            ToggleSettingRow("Vibrate with Music", "Vibration pulses match the music rhythm", vibrateEnabled,
                { scope.launch { AppPreferences.set(AppPreferences.MUSIC_VIBRATE_ENABLED, it) } })
            HorizontalDivider()
            Column(Modifier.padding(16.dp)) {
                Text("Sensitivity", style = MaterialTheme.typography.bodyLarge)
                Slider(value = sensitivity, onValueChange = { sensitivity = it }, modifier = Modifier.fillMaxWidth())
            }
            Card(
                Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("Requires Notification Listener permission and microphone access. Smooth dimming requires Android 13+ on supported devices.",
                    Modifier.padding(16.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ─── HAPTICS ────────────────────────────────────────────────────────────────
@Composable
fun HapticsScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val enabled by AppPreferences.get(AppPreferences.CUSTOM_HAPTICS_ENABLED, false).collectAsState(false)
    val scrollEnabled by AppPreferences.get(AppPreferences.HAPTICS_SCROLL_ENABLED, false).collectAsState(false)
    val intensity by AppPreferences.get(AppPreferences.HAPTICS_INTENSITY, 100).collectAsState(100)
    val patterns = listOf("Click", "Tick", "Heavy Click", "Double Click", "Soft", "Custom")
    var selectedPattern by remember { mutableStateOf(0) }

    Scaffold(topBar = { EverlastingTopBar("Custom Haptics", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            ToggleSettingRow("Custom Tap Haptics", "Haptic feedback on every tap", enabled,
                { scope.launch { AppPreferences.set(AppPreferences.CUSTOM_HAPTICS_ENABLED, it) } })
            HorizontalDivider()
            ToggleSettingRow("Haptics While Scrolling", "Gentle ticks while scrolling", scrollEnabled,
                { scope.launch { AppPreferences.set(AppPreferences.HAPTICS_SCROLL_ENABLED, it) } })
            HorizontalDivider()
            Column(Modifier.padding(16.dp)) {
                Text("Intensity: $intensity%")
                Slider(
                    value = intensity.toFloat(),
                    onValueChange = { scope.launch { AppPreferences.set(AppPreferences.HAPTICS_INTENSITY, it.toInt()) } },
                    valueRange = 10f..200f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            SectionHeader("Pattern")
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                patterns.forEachIndexed { i, p ->
                    FilterChip(selected = selectedPattern == i, onClick = { selectedPattern = i },
                        label = { Text(p, style = MaterialTheme.typography.labelSmall) })
                }
            }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Test Haptic")
            }
        }
    }
}

// ─── CUSTOM SOUNDS ──────────────────────────────────────────────────────────
@Composable
fun CustomSoundsScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val tapEnabled by AppPreferences.get(AppPreferences.TAP_SOUND_ENABLED, false).collectAsState(false)
    val lockEnabled by AppPreferences.get(AppPreferences.LOCK_SOUND_ENABLED, false).collectAsState(false)
    val unlockEnabled by AppPreferences.get(AppPreferences.UNLOCK_SOUND_ENABLED, false).collectAsState(false)

    Scaffold(topBar = { EverlastingTopBar("Custom Sounds", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            SectionHeader("Tap Sound")
            ToggleSettingRow("Custom Tap Sound", "Play sound on every screen tap", tapEnabled,
                { scope.launch { AppPreferences.set(AppPreferences.TAP_SOUND_ENABLED, it) } })
            ListItem(
                headlineContent = { Text("Choose Tap Sound") },
                supportingContent = { Text("Select from your audio files") },
                trailingContent = { TextButton(onClick = {}) { Text("Browse") } }
            )
            HorizontalDivider()
            SectionHeader("Lock / Unlock Sounds")
            ToggleSettingRow("Lock Sound", "Sound when screen locks", lockEnabled,
                { scope.launch { AppPreferences.set(AppPreferences.LOCK_SOUND_ENABLED, it) } })
            ListItem(
                headlineContent = { Text("Choose Lock Sound") },
                trailingContent = { TextButton(onClick = {}) { Text("Browse") } }
            )
            HorizontalDivider()
            ToggleSettingRow("Unlock Sound", "Sound when screen unlocks", unlockEnabled,
                { scope.launch { AppPreferences.set(AppPreferences.UNLOCK_SOUND_ENABLED, it) } })
            ListItem(
                headlineContent = { Text("Choose Unlock Sound") },
                trailingContent = { TextButton(onClick = {}) { Text("Browse") } }
            )
            Card(
                Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("Lock/unlock sounds use Accessibility service for minimal delay. Supports MP3, OGG, WAV formats.",
                    Modifier.padding(16.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ─── NAVBAR OVERLAY ─────────────────────────────────────────────────────────
@Composable
fun NavBarOverlayScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val enabled by AppPreferences.get(AppPreferences.NAVBAR_OVERLAY_ENABLED, false).collectAsState(false)
    val styles = listOf("Transparent", "Frosted Glass", "Colored", "Minimal Pill", "Classic Buttons")
    var selectedStyle by remember { mutableStateOf(0) }
    var pillColor by remember { mutableStateOf("System") }

    Scaffold(topBar = { EverlastingTopBar("Custom Nav Bar", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            ToggleSettingRow(
                "Enable Nav Bar Overlay",
                "Overlay a custom navigation bar using Accessibility",
                enabled,
                { scope.launch { AppPreferences.set(AppPreferences.NAVBAR_OVERLAY_ENABLED, it) } }
            )
            HorizontalDivider()
            SectionHeader("Style")
            styles.forEachIndexed { i, style ->
                ListItem(
                    headlineContent = { Text(style) },
                    leadingContent = { RadioButton(selected = selectedStyle == i, onClick = { selectedStyle = i }) }
                )
                HorizontalDivider()
            }
            Card(
                Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("Uses SYSTEM_ALERT_WINDOW overlay permission. Compatible with both gesture and button navigation modes.",
                    Modifier.padding(16.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ─── SCREENSHOT BLOCKER ─────────────────────────────────────────────────────
@Composable
fun ScreenshotBlockerScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val enabled by AppPreferences.get(AppPreferences.SCREENSHOT_BLOCK_ENABLED, false).collectAsState(false)

    Scaffold(topBar = { EverlastingTopBar("Screenshot Blocker", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            ToggleSettingRow("Block Screenshots in This App", "Prevents screenshots & screen recording",
                enabled, { scope.launch { AppPreferences.set(AppPreferences.SCREENSHOT_BLOCK_ENABLED, it) } })
            HorizontalDivider()
            Card(Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(16.dp)) {
                    Text("🔒 Per-App Screenshot Blocking", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(8.dp))
                    Text("Select apps to block screenshots in. Uses FLAG_SECURE via Accessibility Service.",
                        style = MaterialTheme.typography.bodySmall)
                }
            }
            ListItem(headlineContent = { Text("Select Apps to Protect") },
                supportingContent = { Text("Choose which apps to block screenshots in") },
                trailingContent = { Icon(Icons.Default.ChevronRight, null) }
            )
        }
    }
}

// ─── VOLUME STYLES ──────────────────────────────────────────────────────────
@Composable
fun VolumeStylesScreen(navController: NavController) {
    val styles = listOf("Default", "Compact", "Pill", "Circular", "Minimal", "Expanded")
    var selected by remember { mutableStateOf(0) }

    Scaffold(topBar = { EverlastingTopBar("Volume Styles", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            SectionHeader("Volume Panel Style")
            styles.forEachIndexed { i, style ->
                ListItem(
                    headlineContent = { Text(style) },
                    leadingContent = { RadioButton(selected = selected == i, onClick = { selected = i }) }
                )
                HorizontalDivider()
            }
            Card(Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text("Requires Accessibility Service. Replaces the system volume panel overlay with a custom styled one.",
                    Modifier.padding(16.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ─── HIDDEN FEATURES ────────────────────────────────────────────────────────
@Composable
fun HiddenFeaturesScreen(navController: NavController) {
    val features = listOf(
        Triple("Force Dark Mode", "Force dark mode on all apps", false),
        Triple("Aggressive Doze", "More aggressive battery saving", false),
        Triple("High Touch Sensitivity", "Enable for screen protectors", false),
        Triple("Disable Screenshot Sound", "Silent screenshots", false),
        Triple("Force 60Hz (Battery Save)", "Lock refresh rate to 60Hz", false),
        Triple("Show Tap Circles", "Visual circles on touch for presentations", false),
        Triple("Show Pointer Location", "Overlay showing touch coordinates", false),
        Triple("Disable Bluetooth Scanning", "Stop background BT scanning", false),
    )
    val states = remember { mutableStateListOf(*Array(features.size) { false }) }

    Scaffold(topBar = { EverlastingTopBar("Hidden Android Features", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            Card(Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Text("🔧 Advanced Android settings normally hidden from users. Some require Accessibility or ADB.",
                    Modifier.padding(16.dp), style = MaterialTheme.typography.bodySmall)
            }
            features.forEachIndexed { i, (title, subtitle, _) ->
                ListItem(
                    headlineContent = { Text(title) },
                    supportingContent = { Text(subtitle) },
                    trailingContent = { Switch(checked = states[i], onCheckedChange = { states[i] = it }) }
                )
                HorizontalDivider()
            }
        }
    }
}

// ─── WALLPAPER EFFECTS ──────────────────────────────────────────────────────
@Composable
fun WallpaperEffectsScreen(navController: NavController) {
    val effects = listOf(
        "Pixel Depth Effect" to "3D parallax depth effect like Pixel phones",
        "Dock Blur Wall" to "Blur wallpaper behind dock area",
        "Tint on Lock" to "Tint wallpaper when screen locks",
        "Dim on Notification" to "Dim wallpaper when notifications arrive",
        "Color Shift" to "Shift wallpaper colors with time of day",
        "Vignette Edge" to "Dark vignette around screen edges",
        "Blur Widgets Area" to "Blur behind widget zones"
    )
    val states = remember { mutableStateListOf(*Array(effects.size) { false }) }
    var intensity by remember { mutableFloatStateOf(0.5f) }

    Scaffold(topBar = { EverlastingTopBar("Wallpaper Effects", navController) }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            Column(Modifier.padding(16.dp)) {
                Text("Effect Intensity", style = MaterialTheme.typography.bodyLarge)
                Slider(value = intensity, onValueChange = { intensity = it }, modifier = Modifier.fillMaxWidth())
            }
            HorizontalDivider()
            effects.forEachIndexed { i, (name, desc) ->
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text(desc) },
                    trailingContent = { Switch(checked = states[i], onCheckedChange = { states[i] = it }) }
                )
                HorizontalDivider()
            }
        }
    }
}
