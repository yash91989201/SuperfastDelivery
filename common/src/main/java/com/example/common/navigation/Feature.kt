package com.example.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.common.state_holder.ApplicationStateHolder

interface Feature {
    fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        applicationStateHolder: ApplicationStateHolder
    )
}