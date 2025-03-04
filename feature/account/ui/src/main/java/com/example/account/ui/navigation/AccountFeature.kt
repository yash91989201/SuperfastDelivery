package com.example.account.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.account.ui.screens.home.HomeScreen
import com.example.account.ui.screens.create_profile.CreateProfileScreen
import com.example.account.ui.screens.create_profile.CreateProfileViewModel
import com.example.common.application_state_store.ApplicationStateStore
import com.example.common.navigation.Feature
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest

interface AccountFeature : Feature

class AccountFeatureImpl : AccountFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        applicationStateStore: ApplicationStateStore
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Account>(startDestination = NavigationSubGraphDest.AccountHome) {
            composable<NavigationSubGraphDest.AccountHome> {
                HomeScreen()
            }

            composable<NavigationSubGraphDest.AccountCreateProfile> {
                val viewModel = hiltViewModel<CreateProfileViewModel>()
                CreateProfileScreen(
                    viewModel = viewModel,
                    applicationStateStore = applicationStateStore,
                    onNavigateToSearch = {
                        navHostController.navigate(NavigationSubGraphDest.SearchHome)
                    }
                )
            }
        }
    }
}