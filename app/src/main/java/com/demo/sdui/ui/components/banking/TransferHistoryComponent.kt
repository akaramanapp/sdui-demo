package com.demo.sdui.ui.components.banking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.sdui.data.model.SduiComponent
import com.demo.sdui.data.model.TransferHistoryItem

@Composable
fun TransferHistoryComponent(component: SduiComponent.TransferHistory) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Section header: "Son Transferler  3"
        Row(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = component.title,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color(0xFF1A1A1A)
            )
            Surface(shape = CircleShape, color = Color(0xFFE8E8E8)) {
                Text(
                    text = "${component.count}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    fontSize = 12.sp,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // One card per transfer item
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            component.transfers.forEach { transfer ->
                TransferItem(transfer)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TransferItem(transfer: TransferHistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            // Type badge + date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val (bgColor, fgColor) = when (transfer.type) {
                    "Fast"   -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
                    "EFT"    -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
                    "Havale" -> Color(0xFFFFF3E0) to Color(0xFFE65100)
                    else     -> Color(0xFFF5F5F5) to Color(0xFF555555)
                }
                Surface(shape = RoundedCornerShape(20.dp), color = bgColor) {
                    Text(
                        text = transfer.type,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = fgColor
                    )
                }
                Text(
                    text = transfer.date,
                    fontSize = 13.sp,
                    color = Color(0xFF999999)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Recipient name
            Text(
                text = transfer.name,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color(0xFF1A1A1A)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Masked IBAN
            Text(
                text = transfer.masked_iban,
                fontSize = 13.sp,
                color = Color(0xFF999999)
            )

            Spacer(modifier = Modifier.height(14.dp))

            HorizontalDivider(color = Color(0xFFF0F0F0))

            Spacer(modifier = Modifier.height(14.dp))

            // Son Tutar row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Son Tutar",
                    fontSize = 13.sp,
                    color = Color(0xFF999999)
                )
                Text(
                    text = transfer.amount,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    color = Color(0xFF1A1A1A)
                )
            }
        }
    }
}
