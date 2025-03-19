package com.example.account.ui.screens.privacy_policy

import androidx.lifecycle.ViewModel
import com.example.common.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: PrivacyPolicy.Event) {
        when (event) {
            PrivacyPolicy.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

}

object PrivacyPolicy {
    sealed interface Event {
        data object GoBack : Event
    }
}