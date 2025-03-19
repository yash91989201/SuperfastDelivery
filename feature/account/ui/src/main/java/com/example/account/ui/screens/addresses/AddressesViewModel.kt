package com.example.account.ui.screens.addresses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.ListDeliveryAddress
import com.example.account.domain.use_cases.DeleteDeliveryAddressUseCase
import com.example.account.domain.use_cases.ListDeliveryAddressesUseCase
import com.example.account.domain.use_cases.UpdateDefaultDeliveryAddressUseCase
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.Navigator
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val navigator: Navigator,
    private val listDeliveryAddressesUseCase: ListDeliveryAddressesUseCase,
    private val updateDefaultDeliveryAddressUseCase: UpdateDefaultDeliveryAddressUseCase,
    private val deleteDeliveryAddressUseCase: DeleteDeliveryAddressUseCase,
    applicationStateHolder: ApplicationStateHolder,
) : ViewModel() {

    val auth = applicationStateHolder.authStateHolder.auth
    private val refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1, replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<Addresses.UiState> =
        combine(
            auth.filterNotNull(),
            refreshTrigger.onStart { emit(Unit) }
        ) { auth, _ -> auth.id }
            .flatMapLatest {
                listDeliveryAddressesUseCase(it).map { result ->
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
                navigator.navigateTo(NavigationSubGraphDest.AccountNewAddress())
            }

            is Addresses.Event.UpdateDefaultDeliveryAddress -> {
                updateDefaultDeliveryAddress(deliveryAddressId = event.deliveryAddressId)
            }

            is Addresses.Event.DeleteDeliveryAddress -> {
                deleteDeliveryAddress(addressId = event.addressId)
            }

            is Addresses.Event.EditDeliveryAddress -> {
                editDeliveryAddress(addressId = event.addressId)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun updateDefaultDeliveryAddress(deliveryAddressId: String) {
        auth
            .filterNotNull()
            .flatMapLatest {
                updateDefaultDeliveryAddressUseCase(
                    deliveryAddressId = deliveryAddressId,
                    authId = it.id
                ).map {
                    when (it) {
                        is NetworkResult.Loading -> Addresses.UiState(isLoading = true)
                        is NetworkResult.Success -> {
                            Addresses.UiState(isLoading = false)
                            refreshTrigger.emit(Unit)
                        }

                        is NetworkResult.Error -> Addresses.UiState(
                            isLoading = false,
                            error = UiText.RemoteString(it.message ?: "Unknown Error")
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun editDeliveryAddress(addressId: String) {
        Log.d("Edit delivery address", addressId)
    }

    private fun deleteDeliveryAddress(addressId: String) {
        deleteDeliveryAddressUseCase.invoke(addressId = addressId)
            .onEach {
                when (it) {
                    is NetworkResult.Loading -> Addresses.UiState(isLoading = true)
                    is NetworkResult.Success -> {
                        Addresses.UiState(isLoading = false)
                        refreshTrigger.emit(Unit)
                    }

                    is NetworkResult.Error -> Addresses.UiState(
                        isLoading = false,
                        error = UiText.RemoteString(it.message ?: "Unknown Error")
                    )
                }
            }
            .launchIn(viewModelScope)
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

        data class UpdateDefaultDeliveryAddress(val deliveryAddressId: String) : Event
        data class EditDeliveryAddress(val addressId: String) : Event
        data class DeleteDeliveryAddress(val addressId: String) : Event
    }
}