package com.example.superfastdelivery.di

import com.example.account.ui.navigation.AccountFeature
import com.example.auth.ui.navigation.AuthFeature
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.state_holder.AuthStateHolder
import com.example.common.state_holder.ProfileStateHolder
import com.example.common.state_holder.SessionStateHolder
import com.example.search.ui.navigation.SearchFeature
import com.example.superfastdelivery.ApplicationStateHolderImpl
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
    fun provideApplicationStateHolder(
        sessionStateHolder: SessionStateHolder,
        authStateHolder: AuthStateHolder,
        profileStateHolder: ProfileStateHolder
    ): ApplicationStateHolder {
        return ApplicationStateHolderImpl(
            sessionStateHolder = sessionStateHolder,
            authStateHolder = authStateHolder,
            profileStateHolder = profileStateHolder
        )
    }
}