package com.coolappstore.everlastingandroidtweak.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.data.AppPreferences
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar
import com.coolappstore.everlastingandroidtweak.ui.components.ToggleSettingRow
import kotlinx.coroutines.launch

@Composable
fun ShakeTorchScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val prefs = AppPreferences

    val enabled by prefs.get(prefs.SHAKE_TORCH_ENABLED, false).collectAsState(initial = false)
    val sensitivity by prefs.get(prefs.SHAKE_SENSITIVITY, 12f).collectAsState(initial = 12f)

    var lastShakeInfo by remember { mutableStateOf("Waiting for shake...") }

    Scaffold(
        topBar = { EverlastingTopBar("Shake for Torch", navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            // Status card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (enabled)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.FlashOn,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = if (enabled) MaterialTheme.colorScheme.primary
                               else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Column {
                        Text(
                            if (enabled) "Active — Shake to Toggle Torch" else "Shake for Torch",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            lastShakeInfo,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()

            ToggleSettingRow(
                title = "Enable Shake for Torch",
                subtitle = "Shake phone to toggle flashlight on/off",
                checked = enabled,
                onCheckedChange = { scope.launch { prefs.set(prefs.SHAKE_TORCH_ENABLED, it) } }
            )

            HorizontalDivider()
            Spacer(Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Shake Sensitivity",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Current: ${sensitivity.toInt()} m/s² — Lower = more sensitive",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Slider(
                    value = sensitivity,
                    onValueChange = { scope.launch { prefs.set(prefs.SHAKE_SENSITIVITY, it) } },
                    valueRange = 5f..25f,
                    steps = 19,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Very Sensitive", style = MaterialTheme.typography.labelSmall)
                    Text("Less Sensitive", style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("ℹ️ How it works", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Uses the accelerometer sensor to detect sudden movement. The background service must be running. Works with screen on or off (with Accessibility enabled).",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
