package com.example.account.ui.components.addresses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.example.core.ui.shimmerEffect
import com.example.core.ui.theme.AppTheme

@Composable
fun AddressCardSkeleton() {
    Card(
        shape = AppTheme.shape.medium,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(width = 1.dp, color = AppTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(AppTheme.shape.extraLarge)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(12.dp)
                        .clip(AppTheme.shape.extraLarge)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .clip(AppTheme.shape.extraLarge)
                        .shimmerEffect()
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Lucide.EllipsisVertical,
                contentDescription = "More options",
                tint = AppTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}