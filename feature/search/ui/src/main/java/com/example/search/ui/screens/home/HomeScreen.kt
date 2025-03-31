package com.example.search.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.theme.AppTheme
import com.example.search.ui.components.home.BrowseCategories
import com.example.search.ui.components.home.Header
import com.example.search.ui.components.home.HeaderSkeleton
import com.example.search.ui.components.home.RestaurantCard
import com.example.search.ui.components.home.RestaurantCardSkeleton
import com.example.search.ui.components.home.SearchBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val defaultAddressState by viewModel.defaultAddressState.collectAsStateWithLifecycle()
    val nearbyRestaurantsState by viewModel.nearbyRestaurants.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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

            if (nearbyRestaurantsState.isLoading) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Nearby Restaurants",
                        style = AppTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = AppTheme.colorScheme.primary,
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(5) {
                            RestaurantCardSkeleton()
                        }
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Nearby Restaurants",
                        style = AppTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = AppTheme.colorScheme.primary,
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(nearbyRestaurantsState.data) { shop ->
                            RestaurantCard(shop)
                        }
                    }
                }
            }
        }
    }
}