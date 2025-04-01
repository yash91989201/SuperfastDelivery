package com.example.auth.ui.screens.email_sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.example.core.ui.components.FullScreenLoader
import com.example.core.ui.components.TextInput
import com.example.core.ui.components.TopBar
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.UiText

@Composable
fun EmailSignInScreen(
    viewModel: EmailSignInViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val emailValue by viewModel.email.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar("E-mail Sign In") {
                viewModel.onEvent(EmailSignIn.Event.GoBack)
            }
        },
        modifier = modifier.padding(top = 0.dp, end = 16.dp, bottom = 16.dp, start = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TextInput(
                placeholderText = "abc@xyz.com",
                value = emailValue,
                onValueChange = {
                    viewModel.onEvent(EmailSignIn.Event.UpdateEmail(it))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Lucide.Mail,
                        contentDescription = "Sign In with email",
                        tint = AppTheme.colorScheme.scrim,
                        modifier = Modifier.size(18.dp)
                    )
                },
                keyboardType = KeyboardType.Email,
            )

            if (uiState.error !is UiText.Idle) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = uiState.error.getString(),
                    color = AppTheme.colorScheme.error,
                    style = AppTheme.typography.titleMedium,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                shape = AppTheme.shape.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    viewModel.onEvent(EmailSignIn.Event.SignInWithEmail(emailValue.trim()))
                }
            ) {
                Text(
                    text = "Continue",
                    style = AppTheme.typography.titleMedium,
                )
            }
        }

        if (uiState.isLoading) {
            FullScreenLoader(
                text = "Verifying your E-mail"
            )
        }
    }
}



