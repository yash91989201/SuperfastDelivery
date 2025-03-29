package com.example.auth.ui.screens.email_sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.UiText

@Composable
fun EmailSignInScreen(
    viewModel: EmailSignInViewModel,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val emailValue by viewModel.email.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight()
                .padding(vertical = 32.dp)
                .align(Alignment.Center)
        ) {
            IconButton(
                onClick = { viewModel.onEvent(EmailSignIn.Event.GoBack) },
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
                text = "Continue with Email",
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.onSurface,
                style = AppTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Please enter your email",
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.onSurfaceVariant,
                style = AppTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )

            TextField(
                value = emailValue,
                onValueChange = {
                    viewModel.onEvent(EmailSignIn.Event.UpdateEmail(it))
                },
                singleLine = true,
                shape = AppTheme.shape.medium,
                textStyle = AppTheme.typography.bodyMedium,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = "India",
                        tint = AppTheme.colorScheme.scrim,
                        modifier = Modifier.size(24.dp)
                    )
                },
                placeholder = {
                    Text(
                        text = "abc@xyz.com",
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    //cursorColor = Gray80,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp)
                    .border(
                        width = 1.25.dp,
                        color = AppTheme.colorScheme.outlineVariant,
                        shape = AppTheme.shape.small
                    )
            )

            if (uiState.error !is UiText.Idle) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = uiState.error.getString(),
                    color = AppTheme.colorScheme.error,
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.onEvent(EmailSignIn.Event.SignInWithEmail(emailValue))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Continue",
                    style = AppTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }

        if (uiState.isLoading) {
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
                            text = "Verifying your email",
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



