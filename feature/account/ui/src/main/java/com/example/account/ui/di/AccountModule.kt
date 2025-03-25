package com.example.account.ui.di

import android.content.Context
import com.example.account.ui.BuildConfig
import com.example.account.ui.navigation.AccountFeature
import com.example.account.ui.navigation.AccountFeatureImpl
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    @Singleton
    fun providePlacesClient(@ApplicationContext context: Context): PlacesClient {
        if (!Places.isInitialized()) {
            Places.initializeWithNewPlacesApiEnabled(
                context,
                BuildConfig.GOOGLE_PLACES_API_KEY
            )
        }
        return Places.createClient(context)
    }

    @Provides
    @Singleton
    fun provideAccountFeature(): AccountFeature {
        return AccountFeatureImpl()
    }
}