package com.example.search.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composables.icons.lucide.ImageOff
import com.composables.icons.lucide.Lucide
import com.example.core.ui.theme.AppTheme
import com.example.search.domain.model.Shop
import com.example.search.domain.model.ShopStatus

@Composable
fun RestaurantCard(restaurant: Shop) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surface
        ),
        modifier = Modifier.width(180.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (restaurant.image.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(restaurant.image.first().imageUrl.replace("localhost", "192.168.1.8"))
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(AppTheme.shape.medium),
                    contentDescription = restaurant.image.first().description,
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(AppTheme.shape.medium)
                        .background(AppTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Lucide.ImageOff,
                        contentDescription = "Restaurant image un-available",
                        modifier = Modifier.size(48.dp),
                        tint = AppTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = restaurant.name,
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colorScheme.onSurface
                )

                val statusColor =
                    if (restaurant.shopStatus == ShopStatus.OPEN) AppTheme.colorScheme.tertiary else AppTheme.colorScheme.error

                Text(
                    text = restaurant.shopStatus.toString().lowercase()
                        .replaceFirstChar { it.uppercase() },
                    style = AppTheme.typography.labelMedium,
                    color = AppTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .background(statusColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )

                Text(
                    text = restaurant.address.address,
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
