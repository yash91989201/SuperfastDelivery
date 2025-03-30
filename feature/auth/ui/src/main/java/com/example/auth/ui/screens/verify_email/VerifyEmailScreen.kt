package com.example.auth.ui.screens.verify_email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.auth.ui.components.otp_field.OTP_LENGTH
import com.example.auth.ui.components.otp_field.OtpAction
import com.example.auth.ui.components.otp_field.OtpField
import com.example.auth.ui.components.otp_field.OtpViewModel
import com.example.core.ui.components.FullScreenLoader
import com.example.core.ui.components.TopBar
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.UiText

@Composable
fun VerifyEmailScreen(
    email: String,
    viewModel: VerifyEmailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current
    val focusRequesters = remember { List(OTP_LENGTH) { FocusRequester() } }

    val otpViewModel = hiltViewModel<OtpViewModel>()
    val otpState by otpViewModel.state.collectAsStateWithLifecycle()

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

    Scaffold(
        topBar = {
            TopBar("Verify E-mail") {
                viewModel.onEvent(VerifyEmail.Event.GoBack)
            }
        },
        modifier = modifier.padding(top = 0.dp, end = 16.dp, bottom = 16.dp, start = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

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
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = uiState.verifyOtpError.getString(),
                        color = AppTheme.colorScheme.error,
                        style = AppTheme.typography.titleMedium,
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
                        style = AppTheme.typography.titleMedium,
                    )


                    TextButton(
                        onClick = {}
                    ) {
                        Text(
                            text = "Resend e-mail",
                            style = AppTheme.typography.titleMedium,
                        )
                    }
                }

                if (uiState.resendOtpError !is UiText.Idle) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = uiState.resendOtpError.getString(),
                        color = AppTheme.colorScheme.error,
                        style = AppTheme.typography.titleMedium,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {
                        val otp = otpViewModel.getOtpString() ?: return@Button
                        viewModel.onEvent(VerifyEmail.Event.VerifyEmail(email = email, otp = otp))
                    },
                ) {
                    Text(
                        text = "Login",
                        style = AppTheme.typography.titleMedium,
                    )
                }
            }

            if (uiState.isVerifyingOtp) {
                FullScreenLoader("Verifying OTP")
            }
        }
    }
}