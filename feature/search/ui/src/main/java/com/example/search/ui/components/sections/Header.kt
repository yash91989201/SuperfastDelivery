package com.example.search.ui.components.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.composables.icons.lucide.CircleUserRound
import com.composables.icons.lucide.Lucide
import com.example.common.ui.theme.AppTheme


@Composable
fun Header(imageUrl: String? = null, onSelectDeliveryAddress: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(end = 4.dp)
            .fillMaxWidth(),
    ) {
        Column {
            Text(
                text = "Deliver To",
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.tertiary
            )
            TextButton(
               onClick = onSelectDeliveryAddress
            ) {
                Text(
                    text = "Select delivery location",
                    style = AppTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "List delivery location",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .size(36.dp)
                .background(Color.White, CircleShape)
        ) {
            if (imageUrl.isNullOrEmpty()) {
                Icon(
                    imageVector = Lucide.CircleUserRound,
                    contentDescription = "Profile",
                    modifier = Modifier.size(20.dp)
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HeaderPreview() {
    AppTheme {
        Header(
            onSelectDeliveryAddress = {}
        )
    }
}