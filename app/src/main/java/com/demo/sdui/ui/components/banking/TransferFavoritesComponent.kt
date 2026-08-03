package com.demo.sdui.ui.components.banking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.demo.sdui.data.model.SduiComponent
import com.demo.sdui.data.model.TransferFavorite

@Composable
fun TransferFavoritesComponent(component: SduiComponent.TransferFavorites) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Section header: "Favoriler  5"
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

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = component.favorites, key = { it.id }) { fav ->
                FavoriteCard(fav)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun FavoriteCard(fav: TransferFavorite) {
    val avatarColor = runCatching {
        Color(android.graphics.Color.parseColor(fav.color))
    }.getOrDefault(Color(0xFFE84118))

    Card(
        modifier = Modifier.width(110.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Avatar circle with initials
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(Color(0xFFFEECE8), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = fav.initials ?: "",
                    color = avatarColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            // Label (orange, transfer description)
            Text(
                text = fav.label,
                color = avatarColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            // Full name (gray)
            Text(
                text = fav.name,
                color = Color(0xFF777777),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )
        }
    }
}
