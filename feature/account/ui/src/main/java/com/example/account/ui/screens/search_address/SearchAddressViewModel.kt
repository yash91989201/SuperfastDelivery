package com.example.account.ui.screens.search_address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.UiText
import com.example.core.navigation.NavigationSubGraphDest
import com.example.core.navigation.Navigator
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchAddressViewModel @Inject constructor(
    private val navigator: Navigator,
    private val placesClient: PlacesClient
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val uiState: StateFlow<SearchAddress.UiState> = _searchQuery
        .debounce(400)
        .filter { it.length > 3 }
        .flatMapLatest { fetchAutoCompleteResults(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SearchAddress.UiState()
        )

    fun onEvent(event: SearchAddress.Event) {
        when (event) {
            is SearchAddress.Event.GoBack -> navigator.navigateBack()

            is SearchAddress.Event.UpdateSearchQuery -> {
                _searchQuery.update { event.query }
            }

            is SearchAddress.Event.GoToNewAddressScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountNewAddress(event.placeId))
            }
        }
    }

    private fun fetchAutoCompleteResults(query: String): Flow<SearchAddress.UiState> = flow {
        emit(SearchAddress.UiState(isLoading = true))

        try {
            val request = FindAutocompletePredictionsRequest.builder()
                .setCountries("IN")
                .setQuery(query)
                .build()

            val response = withContext(Dispatchers.IO) {
                placesClient.findAutocompletePredictions(request).await()
            }

            emit(SearchAddress.UiState(data = response.autocompletePredictions))
        } catch (exception: Exception) {
            emit(
                SearchAddress.UiState(
                    error = UiText.RemoteString(
                        exception.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            )
        }
    }
}

object SearchAddress {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: List<AutocompletePrediction> = emptyList()
    )

    sealed interface Event {
        data object GoBack : Event
        data class UpdateSearchQuery(val query: String) : Event
        data class GoToNewAddressScreen(val placeId: String) : Event
    }
}
