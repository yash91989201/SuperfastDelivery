package com.example.restaurant.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.Feature
import com.example.core.navigation.NavigationSubGraph
import com.example.core.navigation.NavigationSubGraphDest
import com.example.restaurant.ui.screens.home.HomeScreen

interface RestaurantFeature : Feature

class RestaurantFeatureImpl : RestaurantFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        startDestination: NavigationSubGraphDest?
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Restaurant>(
            startDestination = startDestination ?: NavigationSubGraphDest.RestaurantHome
        ) {

            composable<NavigationSubGraphDest.RestaurantHome> {
                HomeScreen()
            }
        }
    }
}