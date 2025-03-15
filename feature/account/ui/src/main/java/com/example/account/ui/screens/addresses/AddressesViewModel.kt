package com.example.account.ui.screens.addresses

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val navigator: Navigator,
    private val listDeliveryAddressesUseCase: ListDeliveryAddressesUseCase,
    applicationStateHolder: ApplicationStateHolder,
) : ViewModel() {

    private val _uiState = MutableStateFlow(Addresses.UiState())
    val uiState: StateFlow<Addresses.UiState> get() = _uiState.asStateFlow()
    val authState = applicationStateHolder.authStateHolder.auth

    init {
        listDeliveryAddress()
    }

    fun onEvent(event: Addresses.Event) {
        when (event) {
            Addresses.Event.GoBack -> {
                navigator.navigateBack()
            }

            Addresses.Event.GoToNewAddressScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountNewAddress)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun listDeliveryAddress() {
        authState
            .filterNotNull()
            .flatMapLatest {
                listDeliveryAddressesUseCase(it.id)
            }
            .onEach { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is NetworkResult.Success -> {
                        _uiState.update { it.copy(isLoading = false, data = result.data) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.RemoteString(result.message ?: "Unknown Error")
                            )
                        }
                    }
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

        // Navigation Events
        data object GoBack : Event
        data object GoToNewAddressScreen : Event
    }
}