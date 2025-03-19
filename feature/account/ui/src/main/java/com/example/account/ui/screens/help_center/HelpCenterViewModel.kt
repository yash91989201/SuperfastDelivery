package com.example.account.ui.screens.help_center

import androidx.lifecycle.ViewModel
import com.example.common.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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