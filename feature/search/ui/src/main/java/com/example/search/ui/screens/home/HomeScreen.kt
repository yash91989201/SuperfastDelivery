package com.example.search.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.ui.theme.AppTheme
import com.example.search.ui.components.sections.BrowseCategories
import com.example.search.ui.components.sections.Header
import com.example.search.ui.components.sections.SearchBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val profile by viewModel.profile.collectAsState()

    Scaffold(modifier = modifier.padding(16.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Header(imageUrl = profile?.imageUrl, onSelectDeliveryAddress = {
                viewModel.onEvent(Home.Event.GoToAccountAddressScreen)
            })
            SearchBar(query = "") { }
            BrowseCategories()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            modifier = Modifier,
            viewModel = hiltViewModel<HomeViewModel>(),
        )
    }
}