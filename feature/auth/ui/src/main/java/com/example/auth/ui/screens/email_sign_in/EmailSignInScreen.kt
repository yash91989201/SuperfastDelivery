package com.example.auth.ui.screens.email_sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.common.ui.theme.AppTheme
import com.example.common.ui.theme.Gray80

@Composable
fun EmailSignInScreen(
    onGoBack: () -> Unit,
    onContinue: () -> Unit
) {
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
                value = "",
                onValueChange = {},
                singleLine = true,
                shape = AppTheme.shape.medium,
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
                        text = "example@gmail.com",
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
                    cursorColor = Gray80,
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onContinue,
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
    }
}



