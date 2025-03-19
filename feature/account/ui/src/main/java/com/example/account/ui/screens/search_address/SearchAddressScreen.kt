package com.example.account.ui.screens.search_address

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.example.account.ui.components.search_address.AddressCard
import com.example.common.ui.components.TextInput
import com.example.common.ui.components.TopBar
import com.example.common.utils.UiText

@Composable
fun SearchAddressScreen(
    viewModel: SearchAddressViewModel,
    modifier: Modifier = Modifier,
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar("Search Address") {
                viewModel.onEvent(SearchAddress.Event.GoBack)
            }
        },
        modifier = modifier.padding(top = 0.dp, end = 16.dp, bottom = 16.dp, start = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextInput(
                value = searchQuery,
                onValueChange = { viewModel.onEvent(SearchAddress.Event.UpdateSearchQuery(it)) },
                placeholderText = "Search for an address",
                trailingIcon = {
                    Icon(
                        imageVector = Lucide.Search,
                        contentDescription = "Search",
                        modifier = Modifier.height(18.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error !is UiText.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.error.toString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                uiState.data.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            text = if (searchQuery.isEmpty()) "" else "No addresses found",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                else -> {
                    Log.d("check", "${uiState.data.size}")
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.data) {
                            AddressCard(
                                prediction = it,
                                onClick = {
                                    viewModel.onEvent(SearchAddress.Event.GoToNewAddressScreen(it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}