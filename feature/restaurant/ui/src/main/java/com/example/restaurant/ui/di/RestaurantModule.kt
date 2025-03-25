package com.example.restaurant.ui.di

import com.example.restaurant.ui.navigation.RestaurantFeature
import com.example.restaurant.ui.navigation.RestaurantFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantModule {

    @Provides
    @Singleton
    fun provideRestaurantFeature(): RestaurantFeature {
        return RestaurantFeatureImpl()
    }
}