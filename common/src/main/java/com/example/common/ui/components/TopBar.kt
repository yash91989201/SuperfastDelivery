package com.example.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.example.common.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onGoBack: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = AppTheme.shape.small
                    )
                    .size(36.dp),
                onClick = onGoBack
            ) {
                Icon(
                    imageVector = Lucide.ChevronLeft,
                    contentDescription = "Go back",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        title = {
            Text(
                text = title,
                color = AppTheme.colorScheme.onSurface,
                style = AppTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        },
    )
}