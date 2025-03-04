package com.example.superfastdelivery.di

import com.example.account.ui.navigation.AccountFeature
import com.example.auth.domain.session_state_holder.SessionStateHolder
import com.example.auth.ui.navigation.AuthFeature
import com.example.common.navigation.Feature
import com.example.search.ui.navigation.SearchFeature
import com.example.superfastdelivery.ApplicationStateStore
import com.example.superfastdelivery.ApplicationStateStoreImpl
import com.example.superfastdelivery.navigation.NavigationRoutes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNavigationRoutes(
        authFeature: AuthFeature,
        searchFeature: SearchFeature,
        accountFeature: AccountFeature
    ): NavigationRoutes {
        return NavigationRoutes(authFeature, searchFeature, accountFeature)
    }

    @Provides
    @Singleton
    fun provideApplicationStateStore(
        sessionStateHolder: SessionStateHolder
    ): ApplicationStateStore {
        return ApplicationStateStoreImpl(sessionStateHolder = sessionStateHolder)
    }
}