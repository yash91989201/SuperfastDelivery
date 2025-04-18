package com.example.account.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
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
import com.example.core.ui.components.TopBar
import com.example.core.ui.theme.AppTheme

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
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    profile?.imageUrl?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(56.dp)
                                .clip(AppTheme.shape.extraLarge)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        profile?.let {
                            Text(
                                text = it.name,
                                color = AppTheme.colorScheme.primary,
                                style = AppTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            )
                        }

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
                                    modifier = Modifier.size(16.dp),
                                    tint = AppTheme.colorScheme.secondary
                                )
                                Text(
                                    text = it,
                                    color = AppTheme.colorScheme.secondary,
                                    style = AppTheme.typography.titleSmall,
                                )
                            }
                        }

                    }

                    FilledTonalIconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {
                            viewModel.onEvent(AccountHome.Event.GoToProfileScreen)
                        }
                    ) {
                        Icon(
                            imageVector = Lucide.Pencil,
                            contentDescription = "Edit profile",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                FilledTonalButton(
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
                            contentDescription = "Log Out",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Log Out",
                            style = AppTheme.typography.titleMedium,
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