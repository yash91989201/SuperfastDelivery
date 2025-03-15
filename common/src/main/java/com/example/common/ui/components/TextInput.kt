package com.example.common.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.common.ui.theme.AppTheme

@Composable
fun TextInput(
    value: String?,
    onValueChange: (String) -> Unit,
    placeholderText: String,
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
            textStyle = AppTheme.typography.bodyMedium.copy(
                color = AppTheme.colorScheme.onSurface
            ),
            singleLine = maxLines == 1,
            maxLines = maxLines,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.tertiary.copy(alpha = 0.8f)
                )
            },
            shape = AppTheme.shape.medium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppTheme.colorScheme.surface,
                unfocusedContainerColor = AppTheme.colorScheme.surface,
                disabledContainerColor = AppTheme.colorScheme.surface.copy(alpha = 0.5f),
                cursorColor = AppTheme.colorScheme.primary,
                errorCursorColor = AppTheme.colorScheme.error,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
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
                style = AppTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
        }

        if (maxLength != Int.MAX_VALUE) {
            Text(
                text = "${value?.length}/$maxLength",
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}