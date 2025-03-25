package com.example.superfastdelivery.navigation

import com.example.account.ui.navigation.AccountFeature
import com.example.auth.ui.navigation.AuthFeature
import com.example.restaurant.ui.navigation.RestaurantFeature
import com.example.search.ui.navigation.SearchFeature

data class NavigationRoutes(
    val authFeature: AuthFeature,
    val searchFeature: SearchFeature,
    val accountFeature: AccountFeature,
    val restaurantFeature: RestaurantFeature,
)