package com.example.superfastdelivery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.application_state_store.ApplicationStateStore
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest
import com.example.superfastdelivery.screens.onboarding.OnboardingScreen
import com.example.superfastdelivery.screens.splash.SplashScreen

@Composable
fun AppNavigationRoot(
    applicationStateStore: ApplicationStateStore,
    navigationRoutes: NavigationRoutes
) {
    val navHost = rememberNavController()
    val session by applicationStateStore.sessionStateHolder.session.collectAsState()
    val isLoggedIn = session != null

    NavHost(
        navController = navHost,
        startDestination = NavigationSubGraph.Splash
    ) {

        composable<NavigationSubGraph.Splash> {
            SplashScreen(isLoggedIn = isLoggedIn, navHost = navHost)
        }

        composable<NavigationSubGraph.Onboarding> {
            OnboardingScreen(
                onSignInClick = {
                    navHost.popBackStack()
                    navHost.navigate(NavigationSubGraphDest.AuthSignIn)
                }
            )
        }

        navigationRoutes.authFeature.registerGraph(
            navHostController = navHost,
            navGraphBuilder = this,
            applicationStateStore = applicationStateStore,
        )

        navigationRoutes.searchFeature.registerGraph(
            navHostController = navHost,
            navGraphBuilder = this,
            applicationStateStore = applicationStateStore,
        )

        navigationRoutes.accountFeature.registerGraph(
            navHostController = navHost,
            navGraphBuilder = this,
            applicationStateStore = applicationStateStore
        )
    }
}