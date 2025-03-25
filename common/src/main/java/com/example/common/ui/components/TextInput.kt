package com.example.common.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.X
import com.example.common.ui.theme.AppTheme

@Composable
fun TextInput(
    value: String?,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    showPlaceholder: Boolean = true,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    maxLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
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
        if (!showPlaceholder) {
            Text(
                text = placeholderText,
                style = AppTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
            )
        }

        TextField(
            value = value ?: "",
            onValueChange = {
                if (it.length <= maxLength) onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.25.dp,
                    shape = AppTheme.shape.small,
                    color = borderColor
                ),
            enabled = enabled,
            singleLine = maxLines == 1,
            maxLines = maxLines,
            leadingIcon = leadingIcon,
            placeholder = {
                if (showPlaceholder) {
                    Text(
                        text = placeholderText,
                    )
                }
            },
            trailingIcon = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(if (trailingIcon != null) 6.dp else 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    trailingIcon?.invoke()

                    AnimatedVisibility(visible = !value.isNullOrEmpty()) {
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Clear text input",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            },
            shape = AppTheme.shape.medium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (imeAction == ImeAction.Done) onImeAction() },
                onNext = { if (imeAction == ImeAction.Next) onImeAction() },
                onSearch = { if (imeAction == ImeAction.Search) onImeAction() },
                onSend = { if (imeAction == ImeAction.Send) onImeAction() },
                onGo = { if (imeAction == ImeAction.Go) onImeAction() }
            ),
            visualTransformation = visualTransformation,
            isError = isError,
            interactionSource = interactionSource
        )

        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = AppTheme.colorScheme.error,
                style = AppTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 16.dp, top = 6.dp)
            )
        }

        if (maxLength != Int.MAX_VALUE) {
            Text(
                text = "${value?.length}/$maxLength",
                style = AppTheme.typography.labelMedium,
                color = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}