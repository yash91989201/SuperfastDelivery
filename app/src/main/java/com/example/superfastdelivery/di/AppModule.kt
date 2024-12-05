package com.example.superfastdelivery.di

import com.example.auth.ui.navigation.AuthFeature
import com.example.common.navigation.Feature
import com.example.search.ui.navigation.SearchFeature
import com.example.superfastdelivery.navigation.NavigationRoutes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesNavigationRoutes(
        authFeature: AuthFeature,
        searchFeature: SearchFeature
    ): NavigationRoutes {
        return NavigationRoutes(authFeature, searchFeature)
    }
}