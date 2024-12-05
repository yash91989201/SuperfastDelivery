package com.example.auth.ui.di

import com.example.auth.ui.navigation.AuthFeature
import com.example.auth.ui.navigation.AuthFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun providesAuthModule(): AuthFeature {
        return AuthFeatureImpl()
    }
}