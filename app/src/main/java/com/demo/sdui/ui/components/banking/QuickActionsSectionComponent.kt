package com.demo.sdui.ui.components.banking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.sdui.data.model.BankAction
import com.demo.sdui.data.model.SduiComponent
import com.demo.sdui.ui.LocalActionHandler

@Composable
fun QuickActionsSectionComponent(component: SduiComponent.QuickActionsSection) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 2.dp, bottom = 4.dp)) {
        // ── Tab selector — pill chips ─────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            component.tabLabels.forEachIndexed { index, label ->
                val isSelected = index == selectedTab
                Surface(
                    modifier = Modifier.clickable { selectedTab = index },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) Color.White else Color(0xFFF0F0F0)
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        color = if (isSelected) Color(0xFFE84118) else Color(0xFF888888),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Action items (horizontal scroll grid) ─────────────────────────────
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            component.bankActions.forEach { action ->
                QuickActionItem(action = action)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun QuickActionItem(action: BankAction) {
    val onAction = LocalActionHandler.current
    // Tall card: icon at top, label at bottom — matches reference design
    Surface(
        modifier = Modifier
            .width(110.dp)
            .height(140.dp)
            .clickable { onAction(action.action) },
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon with salmon circle background
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFFFEECE8), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = bankActionIcon(action.icon),
                    contentDescription = null,
                    tint = Color(0xFFE84118),
                    modifier = Modifier.size(28.dp)
                )
            }
            // Label inside card at bottom
            Text(
                text = action.label,
                fontSize = 12.sp,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}
