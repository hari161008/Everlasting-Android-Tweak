package com.coolappstore.everlastingandroidtweak.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.data.AppPreferences
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar
import com.coolappstore.everlastingandroidtweak.ui.components.SectionHeader
import com.coolappstore.everlastingandroidtweak.ui.components.ToggleSettingRow
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val dynamicColor by AppPreferences.get(AppPreferences.DYNAMIC_COLOR, true).collectAsState(true)
    val darkTheme by AppPreferences.get(AppPreferences.DARK_THEME, 0).collectAsState(0)

    Scaffold(topBar = { EverlastingTopBar("Settings", navController) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            SectionHeader("Appearance")
            ToggleSettingRow(
                "Dynamic Color (Material You)",
                "Use wallpaper-based system colors",
                dynamicColor,
                { scope.launch { AppPreferences.set(AppPreferences.DYNAMIC_COLOR, it) } }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("Theme Mode") },
                supportingContent = { Text("System / Light / Dark") },
                trailingContent = {
                    SegmentedButton(darkTheme, listOf("System", "Light", "Dark")) {
                        scope.launch { AppPreferences.set(AppPreferences.DARK_THEME, it) }
                    }
                }
            )
            HorizontalDivider()
            SectionHeader("About")
            ListItem(
                headlineContent = { Text("Everlasting Android Tweak") },
                supportingContent = { Text("Version 1.0.0 • com.coolappstore.everlastingandroidtweak") }
            )
        }
    }
}

@Composable
fun SegmentedButton(selected: Int, options: List<String>, onSelect: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        options.forEachIndexed { i, label ->
            FilterChip(
                selected = selected == i,
                onClick = { onSelect(i) },
                label = { Text(label, style = MaterialTheme.typography.labelSmall) }
            )
        }
    }
}
