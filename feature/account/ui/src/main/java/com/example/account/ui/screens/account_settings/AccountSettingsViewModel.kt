package com.example.account.ui.screens.account_settings

import androidx.lifecycle.ViewModel
import com.example.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: AccountSettings.Event) {
        when (event) {
            AccountSettings.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

}

object AccountSettings {
    sealed interface Event {
        data object GoBack : Event
    }
}