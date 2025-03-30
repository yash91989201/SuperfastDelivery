package com.example.search.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.DefaultDeliveryAddress
import com.example.account.domain.use_cases.GetDefaultDeliveryAddressUseCase
import com.example.core.navigation.NavigationSubGraphDest
import com.example.core.navigation.Navigator
import com.example.core.utils.NetworkResult
import com.example.core.utils.UiText
import com.example.search.domain.model.ListShopsInput
import com.example.search.domain.model.Shop
import com.example.search.domain.model.ShopType
import com.example.search.domain.use_cases.ListShopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getDefaultDeliveryAddressUseCase: GetDefaultDeliveryAddressUseCase,
    private val listShopsUseCase: ListShopsUseCase,
) : ViewModel() {

    private val _defaultAddressState =
        MutableStateFlow<HomeModel.DefaultAddressState>(HomeModel.DefaultAddressState())
    val defaultAddressState: StateFlow<HomeModel.DefaultAddressState> = _defaultAddressState

    private val _nearbyRestaurants =
        MutableStateFlow<HomeModel.NearbyRestaurantsState>(HomeModel.NearbyRestaurantsState())
    val nearbyRestaurants: StateFlow<HomeModel.NearbyRestaurantsState> = _nearbyRestaurants

    init {
        fetchDefaultAddress()
        fetchNearbyRestaurants()
    }

    private fun fetchDefaultAddress() {
        viewModelScope.launch {
            getDefaultDeliveryAddressUseCase()
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

    private fun fetchNearbyRestaurants() {
        viewModelScope.launch {
            listShopsUseCase(
                input = ListShopsInput(
                    shopType = ShopType.RESTAURANT,
                    limit = 3
                )
            )
                .collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _nearbyRestaurants.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }

                        is NetworkResult.Success -> {
                            _nearbyRestaurants.update {
                                it.copy(
                                    isLoading = false,
                                    data = result.data ?: emptyList()
                                )
                            }
                        }

                        is NetworkResult.Error -> {
                            _nearbyRestaurants.update {
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

    data class NearbyRestaurantsState(
        val isLoading: Boolean = false,
        val error: UiText? = UiText.Idle,
        val data: List<Shop> = emptyList(),
    )

    sealed interface Event {
        data object GoToAccountAddressScreen : Event
        data object GoToAccountHomeScreen : Event
    }
}