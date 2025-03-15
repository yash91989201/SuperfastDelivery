package com.example.account.ui.screens.addresses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.example.account.ui.components.addresses.AddressCard
import com.example.account.ui.components.addresses.AddressCardSkeleton
import com.example.common.ui.theme.AppTheme
import com.example.common.utils.UiText

@Composable
fun AddressesScreen(
    modifier: Modifier = Modifier,
    viewModel: AddressesViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = modifier.padding(16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = AppTheme.shape.small
                        )
                        .size(40.dp),
                    onClick = { viewModel.onEvent(Addresses.Event.GoBack) },
                ) {
                    Icon(
                        imageVector = Lucide.ChevronLeft,
                        contentDescription = "Go back",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "My Locations",
                    color = AppTheme.colorScheme.onSurface,
                    style = AppTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }

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
                        val addressList = remember { uiState.data?.deliveryAddress ?: emptyList() }

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            items(addressList, key = { addressList -> addressList.id }) { address ->
                                AddressCard(
                                    title = address.addressAlias,
                                    address = address.address,
                                    selected = address.isDefault,
                                    onSelect = { TODO() },
                                    onEdit = { TODO() },
                                    onDelete = { TODO() }
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

            OutlinedButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = AppTheme.colorScheme.primary,
                ),
                shape = AppTheme.shape.small,
                border = BorderStroke(width = 1.dp, color = AppTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { viewModel.onEvent(Addresses.Event.GoToNewAddressScreen) }
            ) {
                Icon(
                    imageVector = Lucide.Plus,
                    contentDescription = "Add new delivery location",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(18.dp),
                )
                Text(
                    text = "Add New Location",
                    style = AppTheme.typography.labelLarge
                )
            }
        }
    }
}

