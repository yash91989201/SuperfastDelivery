package com.example.account.ui.screens.terms_of_service

import androidx.lifecycle.ViewModel
import com.example.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsOfServiceViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: TermsOfService.Event) {
        when (event) {
            TermsOfService.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

}

object TermsOfService {
    sealed interface Event {
        data object GoBack : Event
    }
}