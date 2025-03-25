package com.example.account.ui.screens.addresses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.example.account.ui.components.addresses.AddressCard
import com.example.account.ui.components.addresses.AddressCardSkeleton
import com.example.common.ui.components.TopBar
import com.example.common.ui.theme.AppTheme
import com.example.common.utils.UiText

@Composable
fun AddressesScreen(
    modifier: Modifier = Modifier,
    viewModel: AddressesViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar("My Addresses") {
                viewModel.onEvent(Addresses.Event.GoBack)
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
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when {
                        uiState.isLoading -> {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(2) { AddressCardSkeleton() }
                            }
                        }

                        uiState.error !is UiText.Idle -> {
                            Text(
                                text = uiState.error.toString(),
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        uiState.data?.deliveryAddress?.isNotEmpty() == true -> {
                            val addressList =
                                remember { uiState.data?.deliveryAddress ?: emptyList() }

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                items(addressList, key = { address -> address.id }) { address ->
                                    AddressCard(
                                        title = address.addressAlias,
                                        address = address.address,
                                        selected = address.isDefault,
                                        onSelect = {
                                            viewModel.onEvent(
                                                Addresses.Event.UpdateDefaultDeliveryAddress(
                                                    deliveryAddressId = address.id
                                                )
                                            )
                                        },
                                        onEdit = {
                                            viewModel.onEvent(
                                                Addresses.Event.EditDeliveryAddress(
                                                    addressId = address.id
                                                )
                                            )
                                        },
                                        onDelete = {
                                            viewModel.onEvent(
                                                Addresses.Event.DeleteDeliveryAddress(
                                                    addressId = address.id
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        else -> {
                            Text(
                                text = "No delivery addresses found.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                Button(
                    shape = AppTheme.shape.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {
                        viewModel.onEvent(Addresses.Event.GoToNewAddressScreen)
                    }
                ) {
                    Icon(
                        imageVector = Lucide.Plus,
                        contentDescription = "Add new delivery address",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(18.dp),
                    )
                    Text(
                        text = "New Address",
                        style = AppTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

