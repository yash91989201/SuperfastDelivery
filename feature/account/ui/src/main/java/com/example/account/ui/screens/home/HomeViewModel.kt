package com.example.account.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.common.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: AccountHome.Event) {
        when (event) {
            AccountHome.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }
}

object AccountHome {
    sealed interface Event {
        data object GoBack : Event
    }
}