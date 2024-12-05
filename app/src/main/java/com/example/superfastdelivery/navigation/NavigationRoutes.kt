package com.example.superfastdelivery.navigation

import com.example.auth.ui.navigation.AuthFeature
import com.example.search.ui.navigation.SearchFeature

data class NavigationRoutes(
    val authFeature: AuthFeature,
    val searchFeature: SearchFeature
)