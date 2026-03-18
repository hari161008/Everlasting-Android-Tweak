package com.coolappstore.everlastingandroidtweak.ui.screens

import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class AppProcess(
    val name: String,
    val packageName: String,
    val memoryMb: Float
)

@Composable
fun TaskManagerScreen(navController: NavController) {
    val context = LocalContext.current
    var processes by remember { mutableStateOf<List<AppProcess>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var totalRam by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val pm = context.packageManager
            val memInfo = ActivityManager.MemoryInfo()
            am.getMemoryInfo(memInfo)
            val totalMb = memInfo.totalMem / 1024 / 1024
            val availMb = memInfo.availMem / 1024 / 1024
            totalRam = "${availMb}MB free / ${totalMb}MB total"

            val runningApps = am.runningAppProcesses ?: emptyList()
            val result = runningApps.mapNotNull { proc ->
                val memArray = intArrayOf(proc.pid)
                val memDebug = am.getProcessMemoryInfo(memArray)
                val memMb = memDebug.firstOrNull()?.totalPss?.div(1024f) ?: 0f
                val label = try {
                    pm.getApplicationLabel(pm.getApplicationInfo(proc.processName.split(":")[0], 0)).toString()
                } catch (_: PackageManager.NameNotFoundException) { proc.processName }
                AppProcess(label, proc.processName, memMb)
            }.sortedByDescending { it.memoryMb }
            processes = result
            loading = false
        }
    }

    Scaffold(topBar = { EverlastingTopBar("Task Manager", navController) }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (totalRam.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Memory, null)
                        Text(totalRam, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            if (loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(processes) { proc ->
                        ListItem(
                            headlineContent = { Text(proc.name) },
                            supportingContent = { Text(proc.packageName, style = MaterialTheme.typography.labelSmall) },
                            trailingContent = {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("${String.format("%.1f", proc.memoryMb)} MB",
                                        style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
