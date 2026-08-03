package com.demo.sdui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.demo.sdui.ui.LocalActionHandler
import com.demo.sdui.ui.SduiScreen
import com.demo.sdui.ui.TransferScreen
import com.demo.sdui.ui.theme.SduiDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SduiDemoTheme {
                var currentScreen by remember { mutableStateOf("dashboard") }

                CompositionLocalProvider(
                    LocalActionHandler provides { action ->
                        when (action) {
                            "quick_transfer" -> currentScreen = "transfer"
                            // future screens can be added here
                        }
                    }
                ) {
                    when (currentScreen) {
                        "transfer" -> TransferScreen(onBack = { currentScreen = "dashboard" })
                        else       -> SduiScreen()
                    }
                }
            }
        }
    }
}
