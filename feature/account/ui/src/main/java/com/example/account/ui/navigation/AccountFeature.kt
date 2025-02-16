package com.example.account.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.account.ui.screens.home.HomeScreen
import com.example.account.ui.screens.profile.ProfileScreen
import com.example.common.navigation.Feature
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest

interface AccountFeature : Feature

class AccountFeatureImpl() : AccountFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Account>(startDestination = NavigationSubGraphDest.AccountHome) {
            composable<NavigationSubGraphDest.AccountHome> {
                HomeScreen()
            }

            composable<NavigationSubGraphDest.AccountProfile> {
                ProfileScreen()
            }
        }
    }

}