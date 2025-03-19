package com.example.account.ui.screens.payment_methods

import androidx.lifecycle.ViewModel
import com.example.common.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentMethodsViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: PaymentMethods.Event) {
        when (event) {
            PaymentMethods.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

}

object PaymentMethods {
    sealed interface Event {
        data object GoBack : Event
    }
}