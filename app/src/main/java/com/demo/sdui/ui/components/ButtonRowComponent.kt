package com.demo.sdui.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demo.sdui.data.model.SduiButton
import com.demo.sdui.data.model.SduiComponent

@Composable
fun ButtonRowComponent(component: SduiComponent.ButtonRow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        component.buttons.forEach { button ->
            SduiButtonItem(
                button = button,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SduiButtonItem(button: SduiButton, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(12.dp)
    val heightModifier = modifier.height(50.dp)
    when (button.style) {
        "primary" -> Button(
            onClick = {},
            modifier = heightModifier,
            shape = shape
        ) {
            Text(text = button.label)
        }
        "secondary" -> FilledTonalButton(
            onClick = {},
            modifier = heightModifier,
            shape = shape
        ) {
            Text(text = button.label)
        }
        "outlined" -> OutlinedButton(
            onClick = {},
            modifier = heightModifier,
            shape = shape
        ) {
            Text(text = button.label)
        }
        else -> Button(
            onClick = {},
            modifier = heightModifier,
            shape = shape
        ) {
            Text(text = button.label)
        }
    }
}
