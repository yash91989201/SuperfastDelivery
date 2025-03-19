package com.example.superfastdelivery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.Navigator
import com.example.common.state_holder.ApplicationStateHolder
import com.example.superfastdelivery.screens.onboarding.OnboardingScreen
import com.example.superfastdelivery.screens.splash.SplashScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigationRoot(
    navigator: Navigator,
    navigationRoutes: NavigationRoutes,
    applicationStateHolder: ApplicationStateHolder
) {
    val navHost = rememberNavController()
    val session by applicationStateHolder.sessionStateHolder.session.collectAsStateWithLifecycle()
    val isLoggedIn = session != null

    LaunchedEffect(Unit) {
        navigator.navigation.collectLatest { destination ->
            when (destination) {
                NavigationSubGraphDest.Back -> {
                    navHost.popBackStack()
                }

                else -> {
                    navHost.navigate(destination) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }

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
        )

        navigationRoutes.searchFeature.registerGraph(
            navHostController = navHost,
            navGraphBuilder = this,
        )

        navigationRoutes.accountFeature.registerGraph(
            navHostController = navHost,
            navGraphBuilder = this,
        )
    }
}