package com.example.account.ui.screens.promotions

import androidx.lifecycle.ViewModel
import com.example.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PromotionsViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: Promotions.Event) {
        when (event) {
            Promotions.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

}

object Promotions {
    sealed interface Event {
        data object GoBack : Event
    }
}