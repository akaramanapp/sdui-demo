package com.demo.sdui.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.sdui.ui.components.SduiComponentRenderer

private val BankOrange = Color(0xFFE84118)

@Composable
fun SduiScreen(viewModel: SduiViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF5F6FA),
        bottomBar = { BankingBottomNavBar() }
    ) { paddingValues ->
        AnimatedContent(
            targetState = uiState,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            label = "sdui_state_transition"
        ) { state ->
            when (state) {
                is UiState.Loading -> LoadingContent()
                is UiState.Success -> SuccessContent(page = state.data)
                is UiState.Error   -> ErrorContent(
                    message = state.message,
                    onRetry = { viewModel.loadPage() }
                )
            }
        }
    }
}

// ─── State composables ────────────────────────────────────────────────────────

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(color = BankOrange)
            Text(
                text = "Yükleniyor...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF888888)
            )
        }
    }
}

@Composable
private fun SuccessContent(page: SduiPageUiModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(page.components) { component ->
            SduiComponentRenderer(component = component)
        }
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Bağlantı hatası",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF888888)
            )
            Button(
                onClick = onRetry,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Text("Tekrar Dene")
            }
        }
    }
}

// ─── Bottom navigation ────────────────────────────────────────────────────────

@Composable
private fun BankingBottomNavBar() {
    var selected by remember { mutableIntStateOf(0) }

    // Outer Box: gives room for FAB to overflow upward
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // White nav surface with proper system gesture inset handling
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                BankNavItem(Icons.Default.Home, "Anasayfa", selected == 0)       { selected = 0 }
                BankNavItem(Icons.Default.SwapHoriz, "İşlemler", selected == 1)    { selected = 1 }
                Spacer(modifier = Modifier.width(60.dp)) // center gap for FAB
                BankNavItem(Icons.Default.AccountBalanceWallet, "Hesap & Kart", selected == 2) { selected = 2 }
                BankNavItem(Icons.Default.AddCircleOutline, "Başvurular", selected == 3)      { selected = 3 }
            }
        }

        // Alba FAB — floats centered, overlapping the top edge of the nav bar
        FloatingActionButton(
            onClick = {},
            containerColor = BankOrange,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-30).dp)
                .size(60.dp)
        ) {
            Text(
                text = "alba",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun BankNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (selected) BankOrange else Color(0xFF9E9E9E)
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = tint,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}
