package com.example.search.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _navigation = Channel<Home.Navigation>()
    val navigation: Flow<Home.Navigation> = _navigation.receiveAsFlow()

    fun onEvent(event: Home.Event) {
        when (event) {
            Home.Event.GoToAccountAddressScreen -> {
                viewModelScope.launch {
                    _navigation.send(Home.Navigation.GoToAccountAddressScreen)
                }
            }
        }
    }

}

object Home {

    sealed interface Navigation {
        data object GoToAccountAddressScreen : Navigation
    }

    sealed interface Event {
        // Navigation events
        data object GoToAccountAddressScreen : Event
    }
}