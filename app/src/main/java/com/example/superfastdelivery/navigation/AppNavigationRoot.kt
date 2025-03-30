package com.example.superfastdelivery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val isSessionLoaded by applicationStateHolder.sessionStateHolder.isSessionLoaded.collectAsStateWithLifecycle()

    if (isSessionLoaded) {
        NavHost(
            navController = navHost,
            startDestination = if (session == null) NavigationSubGraph.Auth else NavigationSubGraph.Search
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
    }
}
