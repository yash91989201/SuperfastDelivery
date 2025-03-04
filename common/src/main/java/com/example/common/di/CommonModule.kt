package com.example.common.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.example.common.application_state_store.ApplicationStateStore
import com.example.common.application_state_store.ApplicationStateStoreImpl
import com.example.common.application_state_store.SessionStateHolder
import com.example.common.dao.AuthDAO
import com.example.common.dao.ProfileDAO
import com.example.common.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("http://172.25.160.1:8081/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .build()

                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideApplicationStateStore(
        sessionStateHolder: SessionStateHolder
    ): ApplicationStateStore {
        return ApplicationStateStoreImpl(sessionStateHolder = sessionStateHolder)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            "superfast_delivery"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAuthDAO(appDatabase: AppDatabase): AuthDAO = appDatabase.authDAO()

    @Provides
    @Singleton
    fun provideProfileDAO(appDatabase: AppDatabase): ProfileDAO = appDatabase.profileDAO()
}