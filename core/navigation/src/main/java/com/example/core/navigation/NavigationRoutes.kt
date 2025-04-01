package com.example.core.navigation

import kotlinx.serialization.Serializable

sealed class NavigationSubGraph {
    @Serializable
    data object Auth : NavigationSubGraph()

    @Serializable
    data object Search : NavigationSubGraph()

    @Serializable
    data object Account : NavigationSubGraph()

    @Serializable
    data object Restaurant : NavigationSubGraph()
}

sealed class NavigationSubGraphDest {
    @Serializable
    object Back : NavigationSubGraphDest()

    @Serializable
    data object AuthHome : NavigationSubGraphDest()

    @Serializable
    data object AuthEmailSignIn : NavigationSubGraphDest()

    @Serializable
    data class AuthVerifyEmail(val email: String) : NavigationSubGraphDest()

    @Serializable
    data class AuthVerifyPhone(val phone: String) : NavigationSubGraphDest()

    @Serializable
    data object SearchHome : NavigationSubGraphDest()

    @Serializable
    data object AccountHome : NavigationSubGraphDest()

    @Serializable
    data object AccountProfile : NavigationSubGraphDest()

    @Serializable
    data object AccountCreateProfile : NavigationSubGraphDest()

    @Serializable
    data object AccountAddresses : NavigationSubGraphDest()

    @Serializable
    data class AccountNewAddress(val placeId: String? = null) : NavigationSubGraphDest()

    @Serializable
    data object AccountSearchAddress : NavigationSubGraphDest()

    @Serializable
    data object AccountPromotions : NavigationSubGraphDest()

    @Serializable
    data object AccountPaymentMethods : NavigationSubGraphDest()

    @Serializable
    data object AccountHelpCenter : NavigationSubGraphDest()

    @Serializable
    data object AccountTermsOfService : NavigationSubGraphDest()

    @Serializable
    data object AccountPrivacyPolicy : NavigationSubGraphDest()

    @Serializable
    data object AccountSettings : NavigationSubGraphDest()

    @Serializable
    data object AccountAboutApp : NavigationSubGraphDest()

    @Serializable
    data object RestaurantHome : NavigationSubGraphDest()
}