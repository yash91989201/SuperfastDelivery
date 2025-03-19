package com.example.account.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.composables.icons.lucide.CircleHelp
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.Pencil
import com.composables.icons.lucide.Phone
import com.composables.icons.lucide.Settings2
import com.composables.icons.lucide.TicketPercent
import com.example.account.ui.components.home.AccountRowItem
import com.example.account.ui.components.home.AccountToggleItem
import com.example.common.ui.components.TopBar
import com.example.common.ui.theme.AppTheme
import com.example.common.ui.theme.Orange20

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val auth by viewModel.auth.collectAsStateWithLifecycle()
    val profile by viewModel.profile.collectAsStateWithLifecycle()

    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var soundEnabled by remember { mutableStateOf(true) }
    var automaticUpdatesEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar("Account") {
                viewModel.onEvent(AccountHome.Event.GoBack)
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
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        profile?.imageUrl?.let {
                            AsyncImage(
                                model = it,
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(AppTheme.shape.extraLarge)
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            profile?.let {
                                Text(
                                    text = it.name,
                                    color = AppTheme.colorScheme.primary,
                                    style = AppTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (auth != null) {
                                auth?.phone?.takeIf { it.isNotEmpty() }?.let {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Lucide.Phone,
                                            contentDescription = "Phone",
                                            modifier = Modifier.size(14.dp),
                                            tint = AppTheme.colorScheme.secondary
                                        )
                                        Text(
                                            text = "+91 $it",
                                            color = AppTheme.colorScheme.tertiary,
                                            style = AppTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                }

                                auth?.email?.takeIf { it.isNotEmpty() }?.let {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Lucide.Mail,
                                            contentDescription = "Email",
                                            modifier = Modifier.size(14.dp),
                                            tint = AppTheme.colorScheme.secondary
                                        )
                                        Text(
                                            text = it,
                                            color = AppTheme.colorScheme.tertiary,
                                            style = AppTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(AppTheme.shape.extraLarge)
                                .background(AppTheme.colorScheme.primary)
                                .clickable {
                                    viewModel.onEvent(AccountHome.Event.GoToProfileScreen)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Lucide.Pencil,
                                contentDescription = "Edit profile",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange20,
                            contentColor = AppTheme.colorScheme.primary
                        ),
                        onClick = {
                            Log.d("TODO", "Logout User")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Lucide.LogOut,
                                contentDescription = "Logout",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Logout",
                                style = AppTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AccountRowItem(
                        title = "My Address",
                        leadingIcon = Lucide.MapPin,
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToAddressesScreen)
                        }
                    )

                    AccountRowItem(
                        title = "My Promotions",
                        leadingIcon = Lucide.TicketPercent,
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToPromotionsScreen)
                        }
                    )

                    AccountRowItem(
                        title = "Payment Methods",
                        leadingIcon = Lucide.MapPin,
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToPaymentMethodsScreen)
                        }
                    )

                    AccountRowItem(
                        title = "Account Settings",
                        leadingIcon = Lucide.Settings2,
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToAccountSettingsScreen)
                        }
                    )

                    AccountRowItem(
                        title = "Help Center",
                        leadingIcon = Lucide.CircleHelp,
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToHelpCenterScreen)
                        }
                    )
                }

                HorizontalDivider()

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AccountToggleItem(
                        title = "Push Notification",
                        isChecked = pushNotificationsEnabled,
                        onCheckedChange = { pushNotificationsEnabled = it }
                    )

                    AccountToggleItem(
                        title = "Dark Mode",
                        isChecked = darkModeEnabled,
                        onCheckedChange = { darkModeEnabled = it }
                    )

                    AccountToggleItem(
                        title = "Sound",
                        isChecked = soundEnabled,
                        onCheckedChange = { soundEnabled = it }
                    )

                    AccountToggleItem(
                        title = "Automatic Updates",
                        isChecked = automaticUpdatesEnabled,
                        onCheckedChange = { automaticUpdatesEnabled = it }
                    )

                    AccountRowItem(
                        title = "Terms of Service",
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToTermsOfServiceScreen)

                        }
                    )

                    AccountRowItem(
                        title = "Privacy Policy",
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToPrivacyPolicyScreen)
                        }
                    )

                    AccountRowItem(
                        title = "About App",
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToAboutAppScreen)
                        }
                    )
                }
            }
        }
    }
}