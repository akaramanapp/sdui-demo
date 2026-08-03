package com.demo.sdui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.demo.sdui.ui.SduiScreen
import com.demo.sdui.ui.theme.SduiDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SduiDemoTheme {
                SduiScreen()
            }
        }
    }
}
