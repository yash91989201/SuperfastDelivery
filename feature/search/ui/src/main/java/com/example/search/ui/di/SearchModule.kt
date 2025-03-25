package com.example.search.ui.di

import com.example.search.ui.navigation.SearchFeature
import com.example.search.ui.navigation.SearchFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchFeature(): SearchFeature {
        return SearchFeatureImpl()
    }
}