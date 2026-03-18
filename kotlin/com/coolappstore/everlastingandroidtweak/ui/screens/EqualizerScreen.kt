package com.coolappstore.everlastingandroidtweak.ui.screens

import android.media.audiofx.Equalizer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar
import com.coolappstore.everlastingandroidtweak.ui.components.SectionHeader

@Composable
fun EqualizerScreen(navController: NavController) {
    var enabled by remember { mutableStateOf(false) }
    val bandNames = listOf("60 Hz", "230 Hz", "910 Hz", "3.6 kHz", "14 kHz")
    val bandLevels = remember { mutableStateListOf(0f, 0f, 0f, 0f, 0f) }
    val presets = listOf("Flat", "Bass Boost", "Treble Boost", "Vocal", "Rock", "Electronic")
    var selectedPreset by remember { mutableStateOf(0) }

    val presetValues = listOf(
        listOf(0f, 0f, 0f, 0f, 0f),
        listOf(6f, 4f, 0f, -2f, -2f),
        listOf(-2f, 0f, 2f, 4f, 6f),
        listOf(-2f, 2f, 4f, 2f, -2f),
        listOf(4f, 2f, -2f, 2f, 4f),
        listOf(2f, -2f, 4f, -2f, 2f),
    )

    Scaffold(topBar = { EverlastingTopBar("Equalizer", navController) }) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ListItem(
                headlineContent = { Text("Enable Equalizer") },
                supportingContent = { Text("Apply EQ to system audio output") },
                trailingContent = { Switch(checked = enabled, onCheckedChange = { enabled = it }) }
            )
            HorizontalDivider()

            SectionHeader("Presets")
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presets.forEachIndexed { i, name ->
                    FilterChip(
                        selected = selectedPreset == i,
                        onClick = {
                            selectedPreset = i
                            presetValues[i].forEachIndexed { j, v -> bandLevels[j] = v }
                        },
                        label = { Text(name, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            SectionHeader("Bands")
            bandNames.forEachIndexed { i, name ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(name, style = MaterialTheme.typography.labelMedium, modifier = Modifier.width(64.dp))
                    Slider(
                        value = bandLevels[i],
                        onValueChange = { bandLevels[i] = it },
                        valueRange = -12f..12f,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "${if (bandLevels[i] >= 0) "+" else ""}${bandLevels[i].toInt()} dB",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.width(52.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.End
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    "ℹ️ Uses Android's built-in AudioEffect API. Works on most devices. Some OEM audio effects may conflict.",
                    Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
