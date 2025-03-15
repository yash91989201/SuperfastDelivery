package com.example.auth.ui.screens.verify_email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.ui.components.otp_field.OTP_LENGTH
import com.example.auth.ui.components.otp_field.OtpAction
import com.example.auth.ui.components.otp_field.OtpField
import com.example.auth.ui.components.otp_field.OtpViewModel
import com.example.common.ui.theme.AppTheme
import com.example.common.utils.UiText

@Composable
fun VerifyEmailScreen(
    email: String,
    viewModel: VerifyEmailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current
    val focusRequesters = remember { List(OTP_LENGTH) { FocusRequester() } }

    val otpViewModel = hiltViewModel<OtpViewModel>()
    val otpState by otpViewModel.state.collectAsState()

    LaunchedEffect(otpState.focusedIndex) {
        otpState.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(otpState.code, keyboardManager) {
        val allNumbersEntered = otpState.code.none { it == null }

        if (allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()

            val otp = otpViewModel.getOtpString()
            if (otp !== null) {
                viewModel.onEvent(VerifyEmail.Event.VerifyEmail(email = email, otp = otp))
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight()
                .padding(vertical = 32.dp)
                .align(Alignment.Center)
        ) {
            IconButton(
                onClick = { viewModel.onEvent(VerifyEmail.Event.GoBack) },
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
                text = "Code has been sent to $email",
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.onSurfaceVariant,
                style = AppTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
            )

            OtpField(
                state = otpState,
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
                    otpViewModel.onAction(action)
                },
            )

            if (uiState.verifyOtpError !is UiText.Idle) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = uiState.verifyOtpError.getString(),
                    color = AppTheme.colorScheme.error,
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Didn't get e-mail?",
                    fontWeight = FontWeight.SemiBold,
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

            if (uiState.resendOtpError !is UiText.Idle) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = uiState.resendOtpError.getString(),
                    color = AppTheme.colorScheme.error,
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    val otp = otpViewModel.getOtpString() ?: return@Button
                    viewModel.onEvent(VerifyEmail.Event.VerifyEmail(email = email, otp = otp))
                },
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

        if (uiState.isVerifyingOtp) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = AppTheme.colorScheme.onSurface.copy(alpha = 0.25f)
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(96.dp)
                        .background(
                            color = Color.White,
                            shape = AppTheme.shape.extraSmall
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(36.dp))

                        Spacer(Modifier.width(24.dp))

                        Text(
                            text = "Verifying OTP",
                            style = AppTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}