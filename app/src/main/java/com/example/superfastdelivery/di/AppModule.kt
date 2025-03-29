package com.example.superfastdelivery.di

import com.example.account.ui.navigation.AccountFeature
import com.example.auth.ui.navigation.AuthFeature
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.app_state.state_holder.AuthStateHolder
import com.example.core.app_state.state_holder.ProfileStateHolder
import com.example.core.app_state.state_holder.SessionStateHolder
import com.example.restaurant.ui.navigation.RestaurantFeature
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
        accountFeature: AccountFeature,
        restaurantFeature: RestaurantFeature
    ): NavigationRoutes {
        return NavigationRoutes(
            authFeature = authFeature,
            searchFeature = searchFeature,
            accountFeature = accountFeature,
            restaurantFeature = restaurantFeature
        )
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