package com.example.account.ui.screens.new_address

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.account.ui.components.new_address.AddressDetailBottomSheet
import com.example.account.ui.components.new_address.GoogleMapView
import com.example.account.ui.components.new_address.SelectedAddressCard
import com.example.common.ui.components.FullScreenLoader
import com.example.common.ui.components.TopBar
import com.example.common.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewAddressScreen(
    modifier: Modifier = Modifier,
    viewModel: NewAddressViewModel,
) {
    val coroutineScope = rememberCoroutineScope()

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val address by viewModel.address.collectAsStateWithLifecycle()
    val latitude by viewModel.latitude.collectAsStateWithLifecycle()
    val longitude by viewModel.longitude.collectAsStateWithLifecycle()
    val addressDetailBottomSheet by viewModel.addressDetailBottomSheet.collectAsStateWithLifecycle()

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (!locationPermissions.allPermissionsGranted || locationPermissions.shouldShowRationale) {
            locationPermissions.launchMultiplePermissionRequest()
        } else {
            coroutineScope.launch {
                val location = viewModel.fetchUserLocation()
                location?.let {
                    viewModel.onEvent(
                        NewAddress.Event.UpdateLocation(
                            lat = it.latitude,
                            lng = it.longitude
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar("Add New Address") {
                viewModel.onEvent(NewAddress.Event.GoBack)
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
                Box(modifier = Modifier.weight(1f)) {
                    GoogleMapView(
                        latitude = latitude,
                        longitude = longitude,
                        onMarkCurrentLocation = {
                            coroutineScope.launch {
                                val location = viewModel.fetchUserLocation()
                                location?.let {
                                    viewModel.onEvent(
                                        NewAddress.Event.UpdateLocation(
                                            lat = location.latitude,
                                            lng = location.longitude
                                        )
                                    )
                                }
                            }
                        },
                        onLocationChange = { latLng ->
                            viewModel.onEvent(
                                NewAddress.Event.UpdateLocation(
                                    latLng.latitude,
                                    latLng.longitude
                                )
                            )
                        }
                    )
                }

                SelectedAddressCard(
                    address = address,
                    onChange = {
                        viewModel.onEvent(NewAddress.Event.GoToSearchAddressScreen)
                    }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = AppTheme.shape.medium,
                    onClick = { viewModel.addressDetailBottomSheet.value = true }
                ) {
                    Text(
                        text = "Add Location Details",
                        style = AppTheme.typography.labelLarge
                    )
                }
            }

            if (addressDetailBottomSheet) {
                AddressDetailBottomSheet(viewModel)
            }

            if (uiState.isLoading) {
                FullScreenLoader(text = "Creating new address...")
            }
        }
    }
}
