package com.example.search.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowRight
import com.composables.icons.lucide.Lucide
import com.example.common.ui.shimmerEffect
import com.example.common.ui.theme.AppTheme

@Composable
fun HeaderSkeleton() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Deliver To",
                    style = AppTheme.typography.labelLarge,
                    color = AppTheme.colorScheme.onSurface
                )

                Icon(
                    imageVector = Lucide.ArrowRight,
                    contentDescription = "Current delivery location",
                    modifier = Modifier.size(16.dp)
                )

                Box(
                    modifier = Modifier
                        .size(width = 60.dp, height = 16.dp)
                        .clip(AppTheme.shape.extraLarge)
                        .shimmerEffect()
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(40.dp)
                    .padding(horizontal = 16.dp)
                    .clip(AppTheme.shape.extraLarge)
                    .shimmerEffect()
            )
        }

        Box(
            modifier = Modifier
                .size(width = 52.dp, height = 36.dp)
                .clip(AppTheme.shape.extraLarge)
                .shimmerEffect()
        )
    }
}
