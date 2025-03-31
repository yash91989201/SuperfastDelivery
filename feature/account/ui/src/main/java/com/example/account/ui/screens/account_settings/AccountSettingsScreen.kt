package com.example.account.ui.screens.account_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ui.components.TopBar

@Composable
fun AccountSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountSettingsViewModel
) {
    Scaffold(
        topBar = {
            TopBar("Account Settings") {
                viewModel.onEvent(AccountSettings.Event.GoBack)
            }
        },
        modifier = modifier.padding(top = 0.dp, end = 16.dp, bottom = 16.dp, start = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text("Account Settings Screen")
        }
    }
}