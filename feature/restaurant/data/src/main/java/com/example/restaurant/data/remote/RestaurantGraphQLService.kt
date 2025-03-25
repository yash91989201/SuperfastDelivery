package com.example.restaurant.data.remote

import com.apollographql.apollo.ApolloClient

interface RestaurantGraphQLService {
}

class RestaurantGraphQLServiceImpl(
    private val apolloClient: ApolloClient
) : RestaurantGraphQLService {}