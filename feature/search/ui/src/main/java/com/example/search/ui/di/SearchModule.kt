package com.example.search.ui.di

import com.example.search.ui.navigation.SearchFeature
import com.example.search.ui.navigation.SearchFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    fun providesSearchFeature(): SearchFeature {
        return SearchFeatureImpl()
    }
}