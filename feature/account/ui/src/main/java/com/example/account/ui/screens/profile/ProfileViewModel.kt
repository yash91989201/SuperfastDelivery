package com.example.account.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.common.navigation.Navigator
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: Profile.Event) {
        when (event) {
            Profile.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }
}

object Profile {
    sealed interface Event {
        data object GoBack : Event
    }
}