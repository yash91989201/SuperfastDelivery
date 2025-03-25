package com.example.restaurant.data.repository

import com.example.restaurant.data.remote.RestaurantGraphQLService
import com.example.restaurant.domain.repository.RestaurantRepository

class RestaurantRepositoryImpl(
    private val restaurantGraphQLService: RestaurantGraphQLService
) : RestaurantRepository {
}