package com.demo.sdui.ui.components.banking

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.sdui.data.model.BankAccount
import com.demo.sdui.data.model.BankAction
import com.demo.sdui.data.model.BankCard
import com.demo.sdui.data.model.SduiComponent

@Composable
fun AccountOverviewComponent(component: SduiComponent.AccountOverview) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // ── Tab bar ──────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFFF0F0F0),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    component.tabs.forEachIndexed { index, tab ->
                        val isSelected = index == selectedTabIndex
                        Surface(
                            color = if (isSelected) Color.White else Color.Transparent,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.clickable { selectedTabIndex = index }
                        ) {
                            Text(
                                text = tab.label,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFFE84118) else Color(0xFF9E9E9E),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Tümü",
                color = Color(0xFFE84118),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }

        // ── Content (animated on tab switch) ─────────────────────────────────
        AnimatedContent(
            targetState = selectedTabIndex,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "account_tab_content"
        ) { tabIndex ->
            when (tabIndex) {
                0    -> AccountsTab(accounts = component.accounts)
                else -> CardsTab(cards = component.bankCards)
            }
        }
    }
}

// ─── Hesaplar tab ─────────────────────────────────────────────────────────────

@Composable
private fun AccountsTab(accounts: List<BankAccount>) {
    val account = accounts.firstOrNull() ?: return
    var balanceVisible by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
            // Account name + visibility toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = account.name,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF444444),
                    fontSize = 15.sp
                )
                IconButton(
                    onClick = { balanceVisible = !balanceVisible },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (balanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Balance display
            if (balanceVisible) {
                val parts = account.balance.split(",")
                val whole = parts.getOrElse(0) { "0" }
                val decimal = parts.getOrElse(1) { "00" }
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 38.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))) {
                            append("₺${whole},")
                        }
                        withStyle(SpanStyle(fontSize = 26.sp, fontWeight = FontWeight.Light, color = Color(0xFF9E9E9E))) {
                            append(decimal)
                        }
                    }
                )
            } else {
                Text(
                    text = "₺ ••••••",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // IBAN with copy icon — bold middle segments
            Row(verticalAlignment = Alignment.CenterVertically) {
                val ibanParts = account.iban.split(" ")
                Text(
                    buildAnnotatedString {
                        ibanParts.forEachIndexed { idx, part ->
                            if (idx > 0) append(" ")
                            if (idx in 3..4) {
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF333333))) { append(part) }
                            } else {
                                withStyle(SpanStyle(fontWeight = FontWeight.Normal, color = Color(0xFF888888))) { append(part) }
                            }
                        }
                    },
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Kopyala",
                    tint = Color(0xFFE84118),
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action chips row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val mainActions = account.actions.dropLast(1)
                val moreAction = account.actions.lastOrNull()

                mainActions.forEach { ba ->
                    AccountActionChip(bankAction = ba, modifier = Modifier.weight(1f))
                }
                if (moreAction != null) {
                    MoreChipButton()
                }
            }
        }
    }
}

@Composable
private fun AccountActionChip(bankAction: BankAction, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF7F7F7),
        border = BorderStroke(0.8.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = bankActionIcon(bankAction.icon),
                contentDescription = null,
                tint = Color(0xFF555555),
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = bankAction.label,
                fontSize = 12.sp,
                color = Color(0xFF333333),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun MoreChipButton() {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF7F7F7),
        border = BorderStroke(0.8.dp, Color(0xFFE0E0E0))
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = bankActionIcon("more_horiz"),
                contentDescription = "Daha fazla",
                tint = Color(0xFF555555),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ─── Kartlar tab ──────────────────────────────────────────────────────────────

@Composable
private fun CardsTab(cards: List<BankCard>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = cards, key = { it.id }) { card ->
            BankCardItem(card = card)
        }
    }
}

@Composable
private fun BankCardItem(card: BankCard) {
    val cardColor = runCatching {
        Color(android.graphics.Color.parseColor(card.color))
    }.getOrDefault(Color(0xFFC9A227))

    Column(modifier = Modifier.width(184.dp)) {
        // Card visual
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(cardColor)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("alBaraka", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    Text(card.card_type, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "**** **** **** ${card.last_four}",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(card.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF1A1A1A))
        Text("**** ${card.last_four}", fontSize = 12.sp, color = Color(0xFF888888))
    }
}
