package com.example.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.common.application_state_store.ApplicationStateStore

interface Feature {
    fun registerGraph(navHostController: NavHostController, navGraphBuilder: NavGraphBuilder, applicationStateStore: ApplicationStateStore)
}