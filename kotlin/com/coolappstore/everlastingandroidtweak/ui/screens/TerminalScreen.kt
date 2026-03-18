package com.coolappstore.everlastingandroidtweak.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coolappstore.everlastingandroidtweak.ui.components.EverlastingTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class TerminalLine(val text: String, val isError: Boolean = false, val isCommand: Boolean = false)

@Composable
fun TerminalScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var input by remember { mutableStateOf("") }
    val lines = remember { mutableStateListOf(
        TerminalLine("Everlasting Terminal v1.0", isCommand = false),
        TerminalLine("Type 'help' for available commands", isCommand = false),
        TerminalLine("⚠️  Root not required for basic commands", isCommand = false),
    ) }

    fun execute(cmd: String) {
        if (cmd.isBlank()) return
        lines.add(TerminalLine("$ $cmd", isCommand = true))
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    when (cmd.trim().lowercase()) {
                        "help" -> "Commands: ls, pwd, date, uname -a, getprop, pm list packages, df, cat /proc/cpuinfo, clear"
                        "clear" -> "CLEAR"
                        else -> {
                            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", cmd))
                            val out = process.inputStream.bufferedReader().readText()
                            val err = process.errorStream.bufferedReader().readText()
                            process.waitFor()
                            if (err.isNotEmpty()) "ERROR:$err" else out.ifEmpty { "(no output)" }
                        }
                    }
                } catch (e: Exception) {
                    "ERROR:${e.message}"
                }
            }
            if (result == "CLEAR") {
                lines.clear()
            } else if (result.startsWith("ERROR:")) {
                lines.add(TerminalLine(result.removePrefix("ERROR:"), isError = true))
            } else {
                result.lines().forEach { lines.add(TerminalLine(it)) }
            }
            listState.animateScrollToItem(lines.size - 1)
        }
    }

    Scaffold(
        topBar = { EverlastingTopBar("Terminal", navController) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$", color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace, modifier = Modifier.padding(end = 8.dp))
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Enter command...", fontFamily = FontFamily.Monospace) },
                    textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 13.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { execute(input); input = "" })
                )
                IconButton(onClick = { execute(input); input = "" }) {
                    Icon(Icons.AutoMirrored.Filled.Send, "Run")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF1A1A2E))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(lines) { line ->
                Text(
                    text = line.text,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = when {
                        line.isCommand -> Color(0xFF64FFDA)
                        line.isError -> Color(0xFFFF5252)
                        else -> Color(0xFFE0E0E0)
                    }
                )
            }
        }
    }
}
