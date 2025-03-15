package com.example.account.ui.screens.new_address

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.example.account.domain.model.AddressAlias
import com.example.account.ui.components.new_address.SelectedAddressCard
import com.example.account.ui.components.new_address.GoogleMapView
import com.example.common.ui.components.DropdownInput
import com.example.common.ui.components.RoundedCheckbox
import com.example.common.ui.components.TextInput
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

    var addressAliasDropdownExpanded by remember { mutableStateOf(false) }

    val receiverName by viewModel.receiverName.collectAsState()
    val receiverPhone by viewModel.receiverPhone.collectAsState()
    val address by viewModel.address.collectAsState()
    val addressAlias by viewModel.addressAlias.collectAsState()
    val otherAlias by viewModel.otherAlias.collectAsState()
    val nearbyLandmark by viewModel.nearbyLandmark.collectAsState()
    val deliveryInstruction by viewModel.deliveryInstruction.collectAsState()
    val isDefault by viewModel.isDefault.collectAsState()
    val latitude by viewModel.latitude.collectAsState()
    val longitude by viewModel.longitude.collectAsState()

    val receiverNameError by viewModel.receiverNameError.collectAsState()
    val receiverPhoneError by viewModel.receiverNameError.collectAsState()
    val addressError by viewModel.addressError.collectAsState()
    val addressAliasError by viewModel.addressAliasError.collectAsState()
    val otherAliasError by viewModel.otherAliasError.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

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

    Scaffold(modifier = modifier.padding(16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = AppTheme.shape.small
                        )
                        .size(40.dp),
                    onClick = { viewModel.onEvent(NewAddress.Event.GoBack) }
                ) {
                    Icon(
                        imageVector = Lucide.ChevronLeft,
                        contentDescription = "Go back",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Add New Location",
                    color = AppTheme.colorScheme.onSurface,
                    style = AppTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }

            GoogleMapView(
                modifier = Modifier
                    .weight(1f)
                    .clip(AppTheme.shape.medium)
                    .background(Color.Gray),
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
                onClick = { showBottomSheet = true }
            ) {
                Text(
                    text = "Add Location Details",
                    style = AppTheme.typography.labelLarge
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                shape = RoundedCornerShape(0.dp),
                containerColor = Color.White,
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                dragHandle = {
                    BottomSheetDefaults.DragHandle(
                        color = AppTheme.colorScheme.onSurfaceVariant
                    )
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(0.85f)
                        .align(Alignment.CenterHorizontally)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Add location details",
                        color = AppTheme.colorScheme.onSurface,
                        style = AppTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TextInput(
                        value = receiverName,
                        onValueChange = { viewModel.receiverName.value = it },
                        placeholderText = "Receiver Name",
                        isError = receiverNameError != null,
                        errorMessage = receiverNameError ?: ""
                    )

                    TextInput(
                        value = receiverPhone,
                        onValueChange = { viewModel.receiverPhone.value = it },
                        placeholderText = "Receiver Phone",
                        keyboardType = KeyboardType.Phone,
                        isError = receiverPhoneError != null,
                        errorMessage = receiverPhoneError ?: ""
                    )

                    TextInput(
                        value = address,
                        onValueChange = { viewModel.address.value = it },
                        placeholderText = "Address",
                        isError = addressError != null,
                        errorMessage = addressError ?: ""
                    )

                    DropdownInput(
                        items = AddressAlias.entries,
                        selectedItem = addressAlias,
                        onItemSelected = { viewModel.addressAlias.value = it },
                        expanded = addressAliasDropdownExpanded,
                        onExpandedChange = { addressAliasDropdownExpanded = it },
                        itemToString = {
                            it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
                        },
                        placeholder = "Select Address Alias",
                        isError = addressAliasError != null,
                        errorMessage = addressAliasError ?: ""
                    )

                    if (addressAlias == AddressAlias.OTHERS) {
                        TextInput(
                            value = otherAlias ?: "",
                            onValueChange = { viewModel.otherAlias.value = it },
                            placeholderText = "Other alias (Optional)",
                            isError = otherAliasError != null,
                            errorMessage = otherAliasError ?: ""
                        )
                    }

                    TextInput(
                        value = nearbyLandmark ?: "",
                        onValueChange = { viewModel.nearbyLandmark.value = it },
                        placeholderText = "Nearby Landmark (Optional)"
                    )

                    TextInput(
                        value = deliveryInstruction ?: "",
                        onValueChange = { viewModel.deliveryInstruction.value = it },
                        placeholderText = "Delivery Instructions (Optional)"
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        RoundedCheckbox(
                            checked = isDefault,
                            onCheckedChange = { viewModel.isDefault.value = it },
                        )
                        Text(
                            text = "Set as Default Address",
                            color = AppTheme.colorScheme.onSurface,
                            style = AppTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }

                Button(
                    shape = AppTheme.shape.medium,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .height(48.dp)
                        .fillMaxWidth(0.85f)
                        .align(Alignment.CenterHorizontally),
                    onClick = { viewModel.onEvent(NewAddress.Event.CreateDeliveryAddress) },
                ) {
                    Text(
                        text = "Save Address",
                        style = AppTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}