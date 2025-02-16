package com.example.account.ui.di

import com.example.account.ui.navigation.AccountFeature
import com.example.account.ui.navigation.AccountFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    fun providesAccountFeature(): AccountFeature {
        return AccountFeatureImpl()
    }
}