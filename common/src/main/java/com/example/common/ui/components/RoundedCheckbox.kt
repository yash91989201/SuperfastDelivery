package com.example.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Lucide
import com.example.common.ui.theme.AppTheme

@Composable
fun RoundedCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val checkedColor = AppTheme.colorScheme.primary
    val uncheckedColor = AppTheme.colorScheme.outlineVariant
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .size(24.dp)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                interactionSource = interactionSource,
                indication = null
            )
            .clip(AppTheme.shape.extraSmall)
            .border(
                width = 1.5.dp,
                color = if (checked) checkedColor else uncheckedColor,
                shape = AppTheme.shape.extraSmall
            )
            .background(
                color = if (checked) checkedColor else Color.Transparent,
                shape = AppTheme.shape.extraSmall
            ),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Lucide.Check,
                contentDescription = "Checked",
                tint = AppTheme.colorScheme.onPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}