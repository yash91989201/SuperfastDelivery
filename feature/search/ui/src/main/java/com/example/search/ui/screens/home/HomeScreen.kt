package com.example.search.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.ui.theme.AppTheme
import com.example.search.ui.components.home.BrowseCategories
import com.example.search.ui.components.home.Header
import com.example.search.ui.components.home.HeaderSkeleton
import com.example.search.ui.components.home.SearchBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {


    val defaultAddressState by viewModel.defaultAddressState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.padding(16.dp),
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
            ) {
                if (defaultAddressState.isLoading) {
                    HeaderSkeleton()
                } else {
                    Header(
                        defaultAddress = defaultAddressState.data,
                        onSelectDeliveryAddress = {
                            viewModel.onEvent(HomeModel.Event.GoToAccountAddressScreen)
                        },
                        onProfileClick = {
                            viewModel.onEvent(HomeModel.Event.GoToAccountHomeScreen)
                        }
                    )
                }

                SearchBar(query = "") { }

                BrowseCategories()
            }
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