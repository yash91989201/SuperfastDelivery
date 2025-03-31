package com.example.core.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.network.NetworkModule
import com.example.core.network.TokenInterceptor
import com.example.core.network.TokenRefreshInterceptor
import com.example.core.network.TokenRefresher
import com.example.core.utils.AppLocationManager
import com.example.core.utils.GeocoderHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDIModule {

    @Provides
    @Singleton
    fun provideGeocoderHelper(@ApplicationContext context: Context): GeocoderHelper {
        return GeocoderHelper(context)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideAppLocationManager(
        @ApplicationContext context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient
    ): AppLocationManager {
        return AppLocationManager(context, fusedLocationProviderClient)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        applicationStateHolder: ApplicationStateHolder
    ): TokenInterceptor {
        return TokenInterceptor(applicationStateHolder)
    }

    @Provides
    @Singleton
    fun provideTokenRefreshInterceptor(
        tokenRefresher: TokenRefresher,
        applicationStateHolder: ApplicationStateHolder,
    ): TokenRefreshInterceptor {
        return TokenRefreshInterceptor(
            tokenRefresher = tokenRefresher,
            applicationStateHolder = applicationStateHolder
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        tokenRefreshInterceptor: TokenRefreshInterceptor
    ): OkHttpClient {
        return NetworkModule.createOkHttpClient(
            tokenInterceptor = tokenInterceptor,
            tokenRefreshInterceptor = tokenRefreshInterceptor
        )
    }

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return NetworkModule.createApolloClient(okHttpClient)
    }
}