package com.example.account.ui.screens.help_center

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.core.navigation.Navigator

@HiltViewModel
class HelpCenterViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: HelpCenter.Event) {
        when (event) {
            HelpCenter.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

}

object HelpCenter {
    sealed interface Event {
        data object GoBack : Event
    }
}