package com.example.account.ui.screens.new_address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.AddressAlias
import com.example.account.domain.model.CreateDeliveryAddressInput
import com.example.account.domain.use_cases.CreateDeliveryAddressUseCase
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.utils.AppLocationManager
import com.example.core.utils.GeocoderHelper
import com.example.core.utils.NetworkResult
import com.example.core.utils.UiText
import com.example.core.navigation.NavigationSubGraphDest
import com.example.core.navigation.Navigator
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewAddressViewModel @Inject constructor(
    private val navigator: Navigator,
    private val placesClient: PlacesClient,
    private val geocoderHelper: GeocoderHelper,
    private val appLocationManager: AppLocationManager,
    private val createDeliveryAddressUseCase: CreateDeliveryAddressUseCase,
    applicationStateHolder: ApplicationStateHolder,
) : ViewModel() {

    val auth = applicationStateHolder.authStateHolder.auth
    private val _uiState: MutableStateFlow<NewAddress.UiState> =
        MutableStateFlow(NewAddress.UiState())
    val uiState: StateFlow<NewAddress.UiState> get() = _uiState.asStateFlow()

    val addressDetailBottomSheet = MutableStateFlow(false)
    val receiverName = MutableStateFlow("")
    val receiverPhone = MutableStateFlow("")
    val address = MutableStateFlow("")
    val addressAlias = MutableStateFlow<AddressAlias?>(null)
    val otherAlias = MutableStateFlow<String?>(null)
    val nearbyLandmark = MutableStateFlow<String?>(null)
    val deliveryInstruction = MutableStateFlow<String?>(null)
    val isDefault = MutableStateFlow(false)
    val latitude = MutableStateFlow(0.0)
    val longitude = MutableStateFlow(0.0)

    // Validation error messages
    val receiverNameError = MutableStateFlow<String?>(null)
    val receiverPhoneError = MutableStateFlow<String?>(null)
    val addressError = MutableStateFlow<String?>(null)
    val addressAliasError = MutableStateFlow<String?>(null)
    val otherAliasError = MutableStateFlow<String?>(null)

    fun onEvent(event: NewAddress.Event) {
        when (event) {
            NewAddress.Event.GoBack -> {
                navigator.navigateBack()
            }

            NewAddress.Event.GoToSearchHomeScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.SearchHome)
            }

            is NewAddress.Event.UpdateLocation -> {
                latitude.value = event.lat
                longitude.value = event.lng

                viewModelScope.launch {
                    geocoderHelper.getAddress(event.lat, event.lng)?.let {
                        address.value = it
                    }
                }
            }

            NewAddress.Event.CreateDeliveryAddress -> {
                if (validateForm()) {
                    createDeliveryAddress()
                }
            }

            NewAddress.Event.GoToSearchAddressScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountSearchAddress)
            }
        }
    }

    suspend fun fetchUserLocation() = withContext(Dispatchers.IO) {
        appLocationManager.getLocation()
    }

    suspend fun fetchPlaceDetails(placeId: String): LatLng? {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        return try {
            val response = placesClient.fetchPlace(request).await()
            response.place.latLng
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (receiverName.value.trim().length < 3) {
            receiverNameError.value = "Name must be at least 3 characters"
            isValid = false
        } else {
            receiverNameError.value = null
        }

        if (!receiverPhone.value.matches(Regex("^[+]?[0-9]{10,15}\$"))) {
            receiverPhoneError.value = "Enter a valid phone number"
            isValid = false
        } else {
            receiverPhoneError.value = null
        }

        if (address.value.isBlank()) {
            addressError.value = "Address is required"
            isValid = false
        } else {
            addressError.value = null
        }

        if (addressAlias.value == null) {
            addressAliasError.value = "Select an address alias"
            isValid = false
        } else {
            addressAliasError.value = null
        }

        if (addressAlias.value == AddressAlias.OTHERS && otherAlias.value.isNullOrBlank()) {
            otherAliasError.value = "Specify an alias"
            isValid = false
        } else {
            otherAliasError.value = null
        }

        return isValid
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createDeliveryAddress() {
        auth
            .filterNotNull()
            .flatMapLatest {
                val newDeliveryAddress = CreateDeliveryAddressInput(
                    receiverName = receiverName.value,
                    receiverPhone = receiverPhone.value,
                    addressAlias = addressAlias.value ?: AddressAlias.HOME,
                    otherAlias = otherAlias.value,
                    latitude = latitude.value,
                    longitude = longitude.value,
                    address = address.value,
                    nearbyLandmark = nearbyLandmark.value,
                    deliveryInstruction = deliveryInstruction.value,
                    isDefault = isDefault.value,
                    authId = it.id
                )
                createDeliveryAddressUseCase(newDeliveryAddress).onEach { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        is NetworkResult.Success -> {
                            _uiState.update { it.copy(isLoading = false) }
                            onEvent(NewAddress.Event.GoBack)
                        }

                        is NetworkResult.Error -> {
                            _uiState.update { it.copy(isLoading = false) }
                        }
                    }

                }
            }
            .launchIn(viewModelScope)
    }
}

object NewAddress {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: String? = null
    )

    sealed interface Event {
        data object GoBack : Event
        data object GoToSearchHomeScreen : Event
        data object GoToSearchAddressScreen : Event

        data class UpdateLocation(val lat: Double, val lng: Double) : Event
        data object CreateDeliveryAddress : Event
    }
}