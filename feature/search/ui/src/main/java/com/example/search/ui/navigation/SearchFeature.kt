package com.example.search.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.navigation.Feature
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest
import com.example.search.ui.screens.home.HomeScreen

interface SearchFeature : Feature

class SearchFeatureImpl : SearchFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Search>(startDestination = NavigationSubGraphDest.SearchHome) {
            composable<NavigationSubGraphDest.SearchHome> {
                HomeScreen()
            }
        }
    }
}