package com.example.search.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.search.ui.screens.home.HomeScreen
import com.example.search.ui.screens.home.HomeViewModel
import com.example.core.navigation.Feature
import com.example.core.navigation.NavigationSubGraph
import com.example.core.navigation.NavigationSubGraphDest
import com.example.core.navigation.Navigator

interface SearchFeature : Feature

class SearchFeatureImpl : SearchFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Search>(startDestination = NavigationSubGraphDest.SearchHome) {
            composable<NavigationSubGraphDest.SearchHome> {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = homeViewModel,
                )
            }
        }
    }
}