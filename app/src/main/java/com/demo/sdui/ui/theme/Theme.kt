package com.demo.sdui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BankOrange      = Color(0xFFE84118)
private val BankOrangeDark  = Color(0xFFC43510)
private val BankOrangeLight = Color(0xFFFFF0ED)

private val LightColors = lightColorScheme(
    primary              = BankOrange,
    onPrimary            = Color.White,
    primaryContainer     = BankOrangeLight,
    onPrimaryContainer   = Color(0xFF4A0A00),
    secondary            = Color(0xFF555555),
    onSecondary          = Color.White,
    secondaryContainer   = Color(0xFFF0F0F0),
    onSecondaryContainer = Color(0xFF1A1A1A),
    background           = Color(0xFFF5F6FA),
    onBackground         = Color(0xFF1A1A1A),
    surface              = Color.White,
    onSurface            = Color(0xFF1A1A1A),
    onSurfaceVariant     = Color(0xFF888888),
    error                = Color(0xFFE84118),
)

private val DarkColors = darkColorScheme(
    primary              = Color(0xFFFFB4A2),
    onPrimary            = Color(0xFF690200),
    primaryContainer     = BankOrangeDark,
    onPrimaryContainer   = Color(0xFFFFDAD5),
    secondary            = Color(0xFFCCC2C0),
    onSecondary          = Color(0xFF342929),
    background           = Color(0xFF1C1B1F),
    surface              = Color(0xFF1C1B1F),
)

@Composable
fun SduiDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
