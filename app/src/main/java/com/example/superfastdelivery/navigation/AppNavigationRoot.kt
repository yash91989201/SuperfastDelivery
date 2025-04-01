package com.example.superfastdelivery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.navigation.NavigationSubGraph
import com.example.core.navigation.NavigationSubGraphDest
import com.example.core.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigationRoot(
    navigator: Navigator,
    navigationRoutes: NavigationRoutes,
    applicationStateHolder: ApplicationStateHolder
) {
    val navHost = rememberNavController()
    val session by applicationStateHolder.sessionStateHolder.session.collectAsStateWithLifecycle()
    val profile by applicationStateHolder.profileStateHolder.profile.collectAsStateWithLifecycle()
    val isSessionLoaded by applicationStateHolder.sessionStateHolder.isSessionLoaded.collectAsStateWithLifecycle()
    val isProfileLoaded by applicationStateHolder.profileStateHolder.isProfileLoaded.collectAsStateWithLifecycle()

    if (isSessionLoaded && isProfileLoaded) {
        val startDestination = if (session != null) {
            if (profile?.authId?.isEmpty() == true) NavigationSubGraph.Account else NavigationSubGraph.Search
        } else NavigationSubGraph.Auth

        NavHost(
            navController = navHost,
            startDestination = startDestination,
        ) {
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
                startDestination = if (profile?.authId?.isEmpty() == true) NavigationSubGraphDest.AccountCreateProfile else null
            )

            navigationRoutes.restaurantFeature.registerGraph(
                navHostController = navHost,
                navGraphBuilder = this,
            )
        }

        LaunchedEffect(navigator, navHost) {
            navigator.navigation.collectLatest { destination ->
                when (destination) {
                    NavigationSubGraphDest.Back -> {
                        if (navHost.canGoBack) {
                            navHost.popBackStack()
                        }
                    }

                    else -> {
                        val currentRoute = navHost.currentBackStackEntry?.destination?.route ?: ""

                        val isNavigatingFromAuth =
                            currentRoute.startsWith(NavigationSubGraph.Auth.toString()) &&
                                    !destination.toString()
                                        .startsWith(NavigationSubGraph.Auth.toString())

                        val isNavigatingFromCreateProfile =
                            currentRoute.contains(NavigationSubGraphDest.AccountCreateProfile.toString())

                        navHost.navigate(destination) {
                            launchSingleTop = true
                            restoreState = true

                            if (isNavigatingFromAuth) {
                                popUpTo(NavigationSubGraph.Auth.toString()) {
                                    inclusive = true
                                }
                            }

                            // TODO when going back from search home if create profile screen exists
                            // then it shows up instead of closing the app, fix it later
                            if (isNavigatingFromCreateProfile) {
                                popUpTo(NavigationSubGraph.Account.toString()) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
