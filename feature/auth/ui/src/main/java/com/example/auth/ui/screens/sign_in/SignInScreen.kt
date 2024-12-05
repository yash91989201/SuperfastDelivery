package com.example.auth.ui.screens.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.auth.ui.R
import com.example.common.ui.theme.AppTheme
import com.example.common.ui.theme.Gray80

@Composable
fun SignInScreen(
    onPhoneSignIn: () -> Unit,
    onEmailSignIn: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.sign_in_img),
                contentDescription = "Sign in and get items delivered",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(260.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = 32.dp)
                .weight(1f)
        ) {
            Text(
                text = "Sign In",
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.primary,
                style = AppTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "Enter your Phone number",
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            TextField(
                value = "",
                onValueChange = {},
                singleLine = true,
                shape = AppTheme.shape.medium,
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.india_flag),
                            contentDescription = "India",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "+91",
                            style = AppTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.colorScheme.tertiary
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = "8547 9621 584",
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
                    .border(
                        width = 1.25.dp,
                        color = AppTheme.colorScheme.outlineVariant,
                        shape = AppTheme.shape.small
                    )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onPhoneSignIn()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Continue",
                    style = AppTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = 24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    thickness = 1.5.dp,
                    color = AppTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Sign in with",
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(
                    thickness = 1.5.dp,
                    color = AppTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Button(
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 3.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = AppTheme.colorScheme.inverseSurface
                    ),
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 6.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google),
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Google",
                        style = AppTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 3.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = AppTheme.colorScheme.inverseSurface
                    ),
                    onClick = onEmailSignIn,
                    modifier = Modifier
                        .weight(1f)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = "Google",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "E-mail",
                        style = AppTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}