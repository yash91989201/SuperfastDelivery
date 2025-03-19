package com.example.account.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.Navigator
import com.example.common.state_holder.ApplicationStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    applicationStateHolder: ApplicationStateHolder,
) : ViewModel() {

    val auth = applicationStateHolder.authStateHolder.auth
    val profile = applicationStateHolder.profileStateHolder.profile

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
                navigator.navigateTo(NavigationSubGraphDest.AccountPromotions)
            }

            AccountHome.Event.GoToPaymentMethodsScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountPaymentMethods)
            }

            AccountHome.Event.GoToAccountSettingsScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountSettings)

            }

            AccountHome.Event.GoToHelpCenterScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountHelpCenter)

            }

            AccountHome.Event.GoToTermsOfServiceScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountTermsOfService)

            }

            AccountHome.Event.GoToPrivacyPolicyScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountPrivacyPolicy)
            }

            AccountHome.Event.GoToAboutAppScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountAboutApp)
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
        data object GoToAccountSettingsScreen : Event
        data object GoToHelpCenterScreen : Event
        data object GoToTermsOfServiceScreen : Event
        data object GoToPrivacyPolicyScreen : Event
        data object GoToAboutAppScreen : Event
    }
}