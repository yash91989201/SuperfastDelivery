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
}

sealed class NavigationSubGraphDest {
    @Serializable
    data object SignIn : NavigationSubGraphDest()

    @Serializable
    data object EmailSignIn : NavigationSubGraphDest()

    @Serializable
    data class VerifyEmail(val email: String) : NavigationSubGraphDest()

    @Serializable
    data object VerifyPhone : NavigationSubGraphDest()

    @Serializable
    data object Search : NavigationSubGraphDest()
}