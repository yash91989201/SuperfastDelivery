package com.example.restaurant.data.di

import com.apollographql.apollo.ApolloClient
import com.example.restaurant.data.remote.RestaurantGraphQLService
import com.example.restaurant.data.remote.RestaurantGraphQLServiceImpl
import com.example.restaurant.data.repository.RestaurantRepositoryImpl
import com.example.restaurant.domain.repository.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantDataModule {

    @Provides
    @Singleton
    fun provideRestaurantGraphQLService(apolloClient: ApolloClient): RestaurantGraphQLService {
        return RestaurantGraphQLServiceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideRestaurantRepositoryImpl(restaurantGraphQLService: RestaurantGraphQLService): RestaurantRepository {
        return RestaurantRepositoryImpl(restaurantGraphQLService)
    }
}