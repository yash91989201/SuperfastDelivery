package com.example.common.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.common.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownInput(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    itemToString: (T) -> String,
    placeholder: String = "Select an option",
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> AppTheme.colorScheme.error
            isFocused -> AppTheme.colorScheme.primary
            else -> AppTheme.colorScheme.outlineVariant
        },
        label = "borderColor"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) onExpandedChange(it) },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedItem?.let(itemToString) ?: placeholder,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                singleLine = true,
                shape = AppTheme.shape.medium,
                textStyle = AppTheme.typography.bodyLarge.copy(
                    color = if (selectedItem != null) AppTheme.colorScheme.onSurface
                    else AppTheme.colorScheme.tertiary
                ),
                leadingIcon = leadingIcon,
                trailingIcon = {
                    if (trailingIcon != null) {
                        trailingIcon()
                    } else {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colorScheme.surface,
                    unfocusedContainerColor = AppTheme.colorScheme.surface,
                    disabledContainerColor = AppTheme.colorScheme.surface.copy(alpha = 0.5f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedTextColor = AppTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                    focusedTextColor = AppTheme.colorScheme.onSurface,
                    errorTextColor = AppTheme.colorScheme.error,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                    .border(
                        width = 1.25.dp,
                        color = if (isError) AppTheme.colorScheme.error else borderColor,
                        shape = AppTheme.shape.small
                    )
                    .clickable(enabled = enabled) { onExpandedChange(!expanded) },
                interactionSource = interactionSource,
                isError = isError
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.background(AppTheme.colorScheme.surface)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = itemToString(item),
                                style = AppTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Normal,
                                color = AppTheme.colorScheme.onSurface
                            )
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        onClick = {
                            onItemSelected(item)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }

        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = AppTheme.colorScheme.error,
                style = AppTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
        }
    }
}