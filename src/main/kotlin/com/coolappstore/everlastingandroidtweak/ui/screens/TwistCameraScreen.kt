package com.coolappstore.everlastingandroidtweak.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar

@Composable
fun TwistCameraScreen(navController: NavController) {
    var enabled by remember { mutableStateOf(false) }

    Scaffold(topBar = { EverlastingTopBar("Twist for Camera", navController) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Row(Modifier.padding(20.dp)) {
                    Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Twist Wrist to Open Camera", style = MaterialTheme.typography.titleMedium)
                        Text("Rotate your wrist twice quickly", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Card {
                Column {
                    ListItem(
                        headlineContent = { Text("Enable Twist Gesture") },
                        supportingContent = { Text("Double wrist twist opens camera") },
                        trailingContent = { Switch(checked = enabled, onCheckedChange = { enabled = it }) }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("Gesture Sensitivity") },
                        supportingContent = { Slider(value = 0.6f, onValueChange = {}, valueRange = 0f..1f) }
                    )
                }
            }

            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Column(Modifier.padding(16.dp)) {
                    Text("💡 Tip", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(4.dp))
                    Text("Uses the gyroscope sensor. Works similarly to Google Pixel & OnePlus quick camera gesture. Requires the background service to be active.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
