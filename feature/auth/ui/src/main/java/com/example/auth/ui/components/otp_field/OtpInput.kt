package com.example.auth.ui.components.otp_field

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.common.ui.theme.AppTheme

@Composable
fun OtpInput(
    number: Int?,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember(key1 = number) {
        mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    index = if (number != null) 1 else 0,
                )
            )
        )
    }

    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = if (isFocused) AppTheme.colorScheme.primary else AppTheme.colorScheme.outlineVariant,
                shape = AppTheme.shape.medium
            )
    ) {
        BasicTextField(
            value = text,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                color = AppTheme.colorScheme.primary,
                fontSize = AppTheme.typography.bodyMedium.fontSize
            ),
            cursorBrush = SolidColor(AppTheme.colorScheme.primary),
            decorationBox = { innerBox ->
                if (!isFocused && number == null) {
                    Text(
                        text = "-",
                        textAlign = TextAlign.Center,
                        color = AppTheme.colorScheme.primary,
                        fontWeight = FontWeight.Light,
                        fontSize = AppTheme.typography.bodyMedium.fontSize,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                } else {
                    innerBox
                }

            },
            onValueChange = { newText ->
                val newNumber = newText.text
                if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                    onNumberChanged(newNumber.toIntOrNull())
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged(it.isFocused)
                }
                .onKeyEvent { event ->
                    val didPressedDelete = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if (didPressedDelete && number == null) {
                        onKeyboardBack()
                    }
                    false
                }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OtpInputPreview() {
    AppTheme {
        OtpInput(
            number = 4,
            focusRequester = FocusRequester(),
            onFocusChanged = {},
            onNumberChanged = {},
            onKeyboardBack = {},
            modifier = Modifier.size(64.dp)
        )
    }
}