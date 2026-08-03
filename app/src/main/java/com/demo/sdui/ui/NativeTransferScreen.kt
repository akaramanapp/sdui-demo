package com.demo.sdui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Hardcoded data (no network, no JSON parsing) ─────────────────────────────

private data class NativeFav(val initials: String, val label: String, val name: String, val color: Color)
private data class NativeTx(val type: String, val date: String, val name: String, val iban: String, val amount: String)

private val NATIVE_FAVS = listOf(
    NativeFav("SS", "Masa tenisi h...", "Sabahattin Sabrioğlu", Color(0xFFE84118)),
    NativeFav("FY", "Annem",           "Fatma Yılmaz",          Color(0xFF9C27B0)),
    NativeFav("MK", "Kira",            "Mehmet Karabacak",      Color(0xFFE84118)),
    NativeFav("MY", "Abim",            "Mahmut Yılmaz",         Color(0xFFFF9800)),
)

private val NATIVE_TXS = listOf(
    NativeTx("Fast",   "03.03.2026", "Sabahattin Sabrioğlu", "TR70 00 \u2022\u2022\u2022\u2022 6972 01", "₺2.000,00"),
    NativeTx("EFT",    "03.03.2026", "Mehmet Karabulut",     "TR12 00 \u2022\u2022\u2022\u2022 6150 01", "₺4.750,00"),
    NativeTx("Havale", "03.03.2026", "Fatma Yılmaz",         "TR23 00 \u2022\u2022\u2022\u2022 8413 36", "₺1.250,00"),
)

// ─── Screen ───────────────────────────────────────────────────────────────────

@Composable
fun NativeTransferScreen(onBack: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF5F6FA)) {
        Column(modifier = Modifier.fillMaxSize()) {
            NativeTopBar(onBack)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item { NativeSearchBar() }
                item { NativeFavoritesSection() }
                item { NativeHistorySection() }
            }
        }
    }
}

@Composable
private fun NativeTopBar(onBack: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), color = Color.White, shadowElevation = 1.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.Close, "Kapat", tint = Color(0xFF1A1A1A), modifier = Modifier.size(22.dp))
            }
            Text(
                text = "Hızlı Para Transferi (Native)",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF1A1A1A)
            )
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
private fun NativeSearchBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(2.dp, Color(0xFFE84118), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Search, null, tint = Color(0xFFE84118), modifier = Modifier.size(18.dp))
            }
            Text("Alıcı, hesap no, açıklama ara", color = Color(0xFFAAAAAA), fontSize = 14.sp)
        }
    }
}

@Composable
private fun NativeFavoritesSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        NativeSectionHeader("Favoriler", 5)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(NATIVE_FAVS.size) { i ->
                NativeFavCard(NATIVE_FAVS[i])
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun NativeFavCard(fav: NativeFav) {
    Card(
        modifier = Modifier.width(110.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(Color(0xFFFEECE8), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(fav.initials, color = fav.color, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Text(
                fav.label, color = fav.color, fontSize = 12.sp,
                fontWeight = FontWeight.Medium, maxLines = 1,
                overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center
            )
            Text(
                fav.name, color = Color(0xFF777777), fontSize = 11.sp,
                textAlign = TextAlign.Center, maxLines = 2,
                overflow = TextOverflow.Ellipsis, lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun NativeHistorySection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        NativeSectionHeader("Son Transferler", 3)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            NATIVE_TXS.forEach { t -> NativeTransferCard(t) }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun NativeTransferCard(t: NativeTx) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            val (bg, fg) = when (t.type) {
                "Fast"   -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
                "EFT"    -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
                else     -> Color(0xFFFFF3E0) to Color(0xFFE65100)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Surface(shape = RoundedCornerShape(20.dp), color = bg) {
                    Text(
                        t.type,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = fg
                    )
                }
                Text(t.date, fontSize = 13.sp, color = Color(0xFF999999))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(t.name, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF1A1A1A))
            Spacer(modifier = Modifier.height(4.dp))
            Text(t.iban, fontSize = 13.sp, color = Color(0xFF999999))
            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Son Tutar", fontSize = 13.sp, color = Color(0xFF999999))
                Text(t.amount, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color(0xFF1A1A1A))
            }
        }
    }
}

@Composable
private fun NativeSectionHeader(title: String, count: Int) {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF1A1A1A))
        Surface(shape = CircleShape, color = Color(0xFFE8E8E8)) {
            Text(
                "$count",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                fontSize = 12.sp, color = Color(0xFF666666), fontWeight = FontWeight.Medium
            )
        }
    }
}
