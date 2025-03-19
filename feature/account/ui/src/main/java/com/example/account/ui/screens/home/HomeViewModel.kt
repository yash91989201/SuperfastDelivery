package com.example.account.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.common.navigation.NavigationSubGraphDest
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

            AccountHome.Event.GoToProfileScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountProfile)
            }

            AccountHome.Event.GoToAddressesScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountAddresses)
            }

            AccountHome.Event.GoToPromotionsScreen -> {
                Log.d("TODO", "promotions screen")
            }

            AccountHome.Event.GoToPaymentMethodsScreen -> {
                Log.d("TODO", "payment methods screen")
            }

            AccountHome.Event.GoToSecurityScreen -> {
                Log.d("TODO", "security screen")
            }

            AccountHome.Event.GoToHelpCenterScreen -> {
                Log.d("TODO", "help center screen")
            }

            AccountHome.Event.GoToTermsOfServiceScreen -> {
                Log.d("TODO", "terms of service screen")
            }

            AccountHome.Event.GoToPrivacyPolicyScreen -> {
                Log.d("TODO", "privacy policy screen")
            }

            AccountHome.Event.GoToAboutAppScreen -> {
                Log.d("TODO", "about app screen")
            }
        }
    }
}

object AccountHome {
    sealed interface Event {
        data object GoBack : Event
        data object GoToProfileScreen : Event
        data object GoToAddressesScreen : Event
        data object GoToPromotionsScreen : Event
        data object GoToPaymentMethodsScreen : Event
        data object GoToSecurityScreen : Event
        data object GoToHelpCenterScreen : Event
        data object GoToTermsOfServiceScreen : Event
        data object GoToPrivacyPolicyScreen : Event
        data object GoToAboutAppScreen : Event
    }
}