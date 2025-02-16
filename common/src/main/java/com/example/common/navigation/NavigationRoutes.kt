package com.example.common.navigation

import kotlinx.serialization.Serializable

sealed class NavigationSubGraph {
    @Serializable
    data object Splash : NavigationSubGraph()

    @Serializable
    data object Onboarding : NavigationSubGraph()

    @Serializable
    data object Auth : NavigationSubGraph()

    @Serializable
    data object Search : NavigationSubGraph()

    @Serializable
    data object Account : NavigationSubGraph()
}

sealed class NavigationSubGraphDest {
    @Serializable
    data object AuthSignIn : NavigationSubGraphDest()

    @Serializable
    data object AuthEmailSignIn : NavigationSubGraphDest()

    @Serializable
    data class AuthVerifyEmail(val email: String) : NavigationSubGraphDest()

    @Serializable
    data object AuthVerifyPhone : NavigationSubGraphDest()

    @Serializable
    data object SearchHome : NavigationSubGraphDest()

    @Serializable
    data object AccountHome : NavigationSubGraphDest()

    @Serializable
    data object AccountProfile : NavigationSubGraphDest()
}