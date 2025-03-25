package com.example.account.ui.components.new_address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.account.domain.model.AddressAlias
import com.example.account.ui.screens.new_address.NewAddress
import com.example.account.ui.screens.new_address.NewAddressViewModel
import com.example.common.ui.components.DropdownInput
import com.example.common.ui.components.RoundedCheckbox
import com.example.common.ui.components.TextInput
import com.example.common.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddressDetailBottomSheet(viewModel: NewAddressViewModel) {

    val receiverName by viewModel.receiverName.collectAsStateWithLifecycle()
    val receiverPhone by viewModel.receiverPhone.collectAsStateWithLifecycle()
    val address by viewModel.address.collectAsStateWithLifecycle()
    val addressAlias by viewModel.addressAlias.collectAsStateWithLifecycle()
    val otherAlias by viewModel.otherAlias.collectAsStateWithLifecycle()
    val nearbyLandmark by viewModel.nearbyLandmark.collectAsStateWithLifecycle()
    val deliveryInstruction by viewModel.deliveryInstruction.collectAsStateWithLifecycle()
    val isDefault by viewModel.isDefault.collectAsStateWithLifecycle()

    val receiverNameError by viewModel.receiverNameError.collectAsStateWithLifecycle()
    val receiverPhoneError by viewModel.receiverNameError.collectAsStateWithLifecycle()
    val addressError by viewModel.addressError.collectAsStateWithLifecycle()
    val addressAliasError by viewModel.addressAliasError.collectAsStateWithLifecycle()
    val otherAliasError by viewModel.otherAliasError.collectAsStateWithLifecycle()

    var addressAliasDropdownExpanded by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        shape = RoundedCornerShape(0.dp),
        containerColor = Color.White,
        sheetState = sheetState,
        onDismissRequest = { viewModel.addressDetailBottomSheet.value = false },
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
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterHorizontally)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Location details",
                color = AppTheme.colorScheme.onSurface,
                style = AppTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 12.dp)
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
                placeholderText = "Select Address Alias",
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
                horizontalArrangement = Arrangement.spacedBy(12.dp),
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
                    style = AppTheme.typography.titleSmall,
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
                style = AppTheme.typography.titleMedium
            )
        }
    }
}
