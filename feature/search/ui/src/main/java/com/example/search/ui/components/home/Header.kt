package com.example.search.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowRight
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.UserRound
import com.example.account.domain.model.DefaultDeliveryAddress
import com.example.core.ui.theme.AppTheme

@Composable
fun Header(
    defaultAddress: DefaultDeliveryAddress? = null,
    onProfileClick: () -> Unit,
    onSelectDeliveryAddress: () -> Unit,
) {

    val address = defaultAddress?.address
    val addressAlias = defaultAddress?.addressAlias

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
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

                addressAlias?.let {
                    Icon(
                        imageVector = Lucide.ArrowRight,
                        contentDescription = "Current delivery location",
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = it.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = AppTheme.typography.labelLarge,
                    )
                }

            }

            TextButton(
                modifier = Modifier.align(Alignment.Start),
                onClick = onSelectDeliveryAddress
            ) {
                Text(
                    text = address ?: "Select Delivery Address",
                    style = AppTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    imageVector = Lucide.ChevronDown,
                    contentDescription = "List delivery location",
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        FilledTonalIconButton(
            onClick = onProfileClick,
            modifier = Modifier.size(width = 60.dp, height = 40.dp)
        ) {
            Icon(
                imageVector = Lucide.UserRound,
                contentDescription = "Go to Account",
                modifier = Modifier
                    .size(32.dp)
                    .padding(vertical = 4.dp)
            )
        }
    }
}