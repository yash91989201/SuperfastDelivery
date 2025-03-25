package com.example.restaurant.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.navigation.Feature
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest
import com.example.restaurant.ui.screens.home.HomeScreen

interface RestaurantFeature : Feature

class RestaurantFeatureImpl : RestaurantFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Restaurant>(startDestination = NavigationSubGraphDest.RestaurantHome) {

            composable<NavigationSubGraphDest.RestaurantHome> {
                HomeScreen()
            }
        }
    }
}