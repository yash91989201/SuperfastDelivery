package com.example.search.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.common.models.Profile
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.Navigator
import com.example.common.state_holder.ApplicationStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    applicationStateHolder: ApplicationStateHolder
) : ViewModel() {

    private val _profile = applicationStateHolder.profileStateHolder.profile
    val profile: StateFlow<Profile?> = _profile

    fun onEvent(event: Home.Event) {
        when (event) {
            Home.Event.GoToAccountAddressScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountAddresses)
            }
        }
    }
}

object Home {

    sealed interface Event {
        // Navigation events
        data object GoToAccountAddressScreen : Event
    }
}