package com.coolappstore.everlastingandroidtweak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.coolappstore.everlastingandroidtweak.data.AppPreferences
import com.coolappstore.everlastingandroidtweak.ui.navigation.EverlastingNavHost
import com.coolappstore.everlastingandroidtweak.ui.theme.EverlastingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dynamicColor by AppPreferences.get(AppPreferences.DYNAMIC_COLOR, true)
                .collectAsState(initial = true)
            val darkThemePref by AppPreferences.get(AppPreferences.DARK_THEME, 0)
                .collectAsState(initial = 0)
            val systemDark = isSystemInDarkTheme()
            val isDark = when (darkThemePref) {
                1 -> false
                2 -> true
                else -> systemDark
            }

            EverlastingTheme(
                darkTheme = isDark,
                dynamicColor = dynamicColor
            ) {
                EverlastingNavHost()
            }
        }
    }
}
