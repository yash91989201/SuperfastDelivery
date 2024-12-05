package com.example.auth.ui.screens.verify_email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.ui.components.otp_field.OtpAction
import com.example.auth.ui.components.otp_field.OtpField
import com.example.auth.ui.components.otp_field.OtpViewModel
import com.example.common.ui.theme.AppTheme

@Composable
fun VerifyEmailScreen(
    onGoBack: () -> Unit
) {

    val otpViewModel = hiltViewModel<OtpViewModel>()
    val state by otpViewModel.state.collectAsState()

    val focusRequesters = remember { List(4) { FocusRequester() } }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = state.code, key2 = keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if (allNumbersEntered) {
            focusRequesters.forEach { it.freeFocus() }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    LaunchedEffect(key1 = state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .weight(1f)
                .padding(vertical = 24.dp)
        ) {
            IconButton(
                onClick = onGoBack,
                modifier = Modifier
                    .shadow(8.dp, shape = AppTheme.shape.medium)
                    .background(color = Color.White, shape = AppTheme.shape.medium)
                    .size(48.dp)
                    .align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                    contentDescription = "Go back",
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Verify e-mail",
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.onSurface,
                style = AppTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Code has been sent to example@gmail.com",
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.onSurfaceVariant,
                style = AppTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
            )

            OtpField(
                state = state,
                focusRequesters = focusRequesters,
                onAction = { action ->
                    when (action) {
                        is OtpAction.OnEnterNumber -> {
                            if (action.number != null) {
                                focusRequesters[action.index].freeFocus()
                            }
                        }

                        else -> Unit
                    }
                },
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Didn't get e-mail?",
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colorScheme.onSurfaceVariant,
                    style = AppTheme.typography.titleMedium,
                )

                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = AppTheme.colorScheme.primary
                    ),
                    onClick = {}
                ) {
                    Text(
                        text = "Resend e-mail",
                        fontWeight = FontWeight.Bold,
                        style = AppTheme.typography.titleMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    style = AppTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}