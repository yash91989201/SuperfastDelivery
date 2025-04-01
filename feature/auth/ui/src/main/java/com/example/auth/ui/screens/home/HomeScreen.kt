package com.example.auth.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.example.auth.ui.R
import com.example.core.ui.components.FullScreenLoader
import com.example.core.ui.components.TextInput
import com.example.core.ui.theme.AppTheme
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.i18n.phonenumbers.PhoneNumberUtil

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    val localContext = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val phoneValue by viewModel.phone.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->

        try {
            val fullNumber =
                Identity.getSignInClient(localContext).getPhoneNumberFromIntent(result.data)

            val phoneUtil = PhoneNumberUtil.getInstance()
            val numberProto = phoneUtil.parse(fullNumber, null)

            val nationalNumber = numberProto.nationalNumber.toString()

            viewModel.onEvent(HomeModel.Event.UpdatePhone(nationalNumber))
        } catch (_: Exception) {
            keyboardController?.show()
        }
    }


    val getPhoneNumberHint = {
        val request = GetPhoneNumberHintIntentRequest
            .builder()
            .build()

        Identity
            .getSignInClient(localContext)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { intent ->
                launcher.launch(
                    IntentSenderRequest.Builder(intent).build()
                )
            }
            .addOnFailureListener {
                keyboardController?.show()
            }
    }

    Scaffold(
        modifier = modifier.padding(0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.auth_home_banner),
                    contentDescription = "Sign in and get items delivered",
                    contentScale = ContentScale.Crop
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
                    style = AppTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                TextInput(
                    label = "Phone Number",
                    placeholderText = "85479 621584",
                    value = phoneValue,
                    onValueChange = {
                        viewModel.onEvent(HomeModel.Event.UpdatePhone(it))
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.india_flag),
                            contentDescription = "India",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    keyboardType = KeyboardType.Phone,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.hide()
                                getPhoneNumberHint()
                            }
                        }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(HomeModel.Event.SignInWithPhone(phoneValue))
                    },
                    shape = AppTheme.shape.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Continue",
                        style = AppTheme.typography.titleMedium
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(vertical = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = "Or Sign In With",
                        style = AppTheme.typography.titleSmall
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ElevatedButton(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.google),
                            contentDescription = "Sign in with Google",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Google",
                            style = AppTheme.typography.titleMedium,
                        )
                    }

                    ElevatedButton(
                        onClick = {
                            viewModel.onEvent(HomeModel.Event.GoToEmailSignInScreen)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(
                            imageVector = Lucide.Mail,
                            contentDescription = "Sign in with email",
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "E-mail",
                            style = AppTheme.typography.titleMedium,
                        )
                    }
                }

            }
        }

        if (uiState.isLoading) {
            FullScreenLoader(
                text = "Verifying your E-mail"
            )
        }
    }
}