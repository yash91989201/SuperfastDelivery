package com.example.account.ui.screens.addresses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.ListDeliveryAddress
import com.example.account.domain.use_cases.ListDeliveryAddressesUseCase
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.Navigator
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val navigator: Navigator,
    private val listDeliveryAddressesUseCase: ListDeliveryAddressesUseCase,
    applicationStateHolder: ApplicationStateHolder,
) : ViewModel() {

    val auth = applicationStateHolder.authStateHolder.auth

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<Addresses.UiState> = auth.filterNotNull().flatMapLatest {
        listDeliveryAddressesUseCase(it.id).map { result ->
            when (result) {
                is NetworkResult.Loading -> Addresses.UiState(isLoading = true)
                is NetworkResult.Success -> Addresses.UiState(
                    isLoading = false, data = result.data
                )

                is NetworkResult.Error -> Addresses.UiState(
                    isLoading = false,
                    error = UiText.RemoteString(result.message ?: "Unknown Error")
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Addresses.UiState(isLoading = true)
    )

    fun onEvent(event: Addresses.Event) {
        when (event) {
            Addresses.Event.GoBack -> {
                navigator.navigateBack()
            }

            Addresses.Event.GoToNewAddressScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountNewAddress)
            }

            is Addresses.Event.SetDeliveryAddressAsDefault -> {
                setDeliveryAddressAsDefault(addressId = event.addressId)
            }

            is Addresses.Event.DeleteDeliveryAddress -> {
                deleteDeliveryAddress(addressId = event.addressId)
            }

            is Addresses.Event.EditDeliveryAddress -> {
                editDeliveryAddress(addressId = event.addressId)
            }
        }
    }

    private fun setDeliveryAddressAsDefault(addressId: String) {
        Log.d("Set delivery address as default", addressId)
    }

    private fun editDeliveryAddress(addressId: String) {
        Log.d("Edit delivery address", addressId)
    }

    private fun deleteDeliveryAddress(addressId: String) {
        Log.d("Delete delivery address", addressId)

    }
}

object Addresses {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: ListDeliveryAddress? = null
    )

    sealed interface Event {
        data object GoBack : Event
        data object GoToNewAddressScreen : Event

        data class SetDeliveryAddressAsDefault(val addressId: String) : Event
        data class EditDeliveryAddress(val addressId: String) : Event
        data class DeleteDeliveryAddress(val addressId: String) : Event
    }
}