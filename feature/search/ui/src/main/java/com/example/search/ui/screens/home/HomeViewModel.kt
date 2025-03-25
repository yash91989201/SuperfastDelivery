package com.example.search.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.DefaultDeliveryAddress
import com.example.account.domain.use_cases.GetDefaultDeliveryAddressUseCase
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.Navigator
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    applicationStateHolder: ApplicationStateHolder,
    private val getDefaultDeliveryAddressUseCase: GetDefaultDeliveryAddressUseCase,
) : ViewModel() {

    private val _defaultAddressState =
        MutableStateFlow<HomeModel.DefaultAddressState>(HomeModel.DefaultAddressState())
    val defaultAddressState: StateFlow<HomeModel.DefaultAddressState> = _defaultAddressState

    val auth = applicationStateHolder.authStateHolder.auth

    init {
        fetchDefaultAddress()
    }

    private fun fetchDefaultAddress() {
        viewModelScope.launch {
            auth.collectLatest { auth ->
                if (auth == null) {
                    _defaultAddressState.update { it.copy(error = UiText.RemoteString(message = "Not logged in")) }
                    return@collectLatest
                }

                getDefaultDeliveryAddressUseCase(auth.id)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Loading -> {
                                _defaultAddressState.update {
                                    it.copy(
                                        isLoading = true,
                                    )
                                }
                            }

                            is NetworkResult.Success -> {
                                _defaultAddressState.update {
                                    it.copy(
                                        isLoading = false,
                                        data = result.data
                                    )
                                }
                            }

                            is NetworkResult.Error -> {
                                _defaultAddressState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = UiText.RemoteString(message = "Error")
                                    )
                                }
                            }
                        }
                    }
            }
        }

    }

    fun onEvent(event: HomeModel.Event) {
        when (event) {
            HomeModel.Event.GoToAccountAddressScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountAddresses)
            }

            HomeModel.Event.GoToAccountHomeScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountHome)
            }
        }
    }

}

object HomeModel {

    data class DefaultAddressState(
        val isLoading: Boolean = false,
        val error: UiText? = UiText.Idle,
        val data: DefaultDeliveryAddress? = null,
    )

    sealed interface Event {
        data object GoToAccountAddressScreen : Event
        data object GoToAccountHomeScreen : Event
    }
}