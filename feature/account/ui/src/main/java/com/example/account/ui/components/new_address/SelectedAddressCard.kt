package com.example.account.ui.components.new_address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPinned
import com.example.core.ui.theme.AppTheme

@Composable
fun SelectedAddressCard(address: String?, onChange: () -> Unit) {
    Card(
        shape = AppTheme.shape.medium,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surfaceContainerHighest
        ),
        border = BorderStroke(1.dp, AppTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(horizontal = AppTheme.size.medium, vertical = AppTheme.size.small)
                .fillMaxWidth(),
        ) {
            Icon(
                imageVector = Lucide.MapPinned,
                contentDescription = "Selected Location",
                tint = AppTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = address ?: "Locating address...",
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            TextButton(
                onClick = onChange,
                modifier = Modifier.defaultMinSize(minWidth = 0.dp, minHeight = 0.dp)
            ) {
                Text(
                    text = "Change",
                    style = AppTheme.typography.titleSmall
                )
            }
        }
    }
}
