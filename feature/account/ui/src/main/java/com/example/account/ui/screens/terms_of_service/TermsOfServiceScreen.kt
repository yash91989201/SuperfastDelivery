package com.example.account.ui.screens.terms_of_service

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
fun TermsOfServiceScreen(
    modifier: Modifier = Modifier,
    viewModel: TermsOfServiceViewModel
) {
    Scaffold(
        topBar = {
            TopBar("Terms Of Service") {
                viewModel.onEvent(TermsOfService.Event.GoBack)
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
            Text("Terms Of Service screen")
        }
    }
}