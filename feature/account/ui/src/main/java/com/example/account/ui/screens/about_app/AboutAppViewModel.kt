package com.example.account.ui.screens.about_app

import androidx.lifecycle.ViewModel
import com.example.common.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutAppViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: AboutApp.Event) {
        when (event) {
            AboutApp.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

}

object AboutApp {
    sealed interface Event {
        data object GoBack : Event
    }
}