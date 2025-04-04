package com.example.auth.data.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.example.auth.data.TokenRefresherImpl
import com.example.auth.data.data_store.AuthDataStoreImpl
import com.example.auth.data.data_store.SessionDataStoreImpl
import com.example.auth.data.remote.AuthGraphQLService
import com.example.auth.data.remote.AuthGraphQLServiceImpl
import com.example.auth.data.repository.AuthRepositoryImpl
import com.example.auth.data.state_holder.AuthStateHolderImpl
import com.example.auth.data.state_holder.SessionStateHolderImpl
import com.example.auth.domain.repository.AuthRepository
import com.example.core.app_state.data_store.AuthDataStore
import com.example.core.app_state.data_store.SessionDataStore
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.app_state.state_holder.AuthStateHolder
import com.example.core.app_state.state_holder.SessionStateHolder
import com.example.core.network.TokenRefresher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Provides
    @Singleton
    fun provideAuthDataStore(@ApplicationContext context: Context): AuthDataStore {
        return AuthDataStoreImpl(context)
    }

    @Provides
    @Singleton
    fun provideAuthStateHolder(authDataStore: AuthDataStore): AuthStateHolder {
        return AuthStateHolderImpl(authDataStore = authDataStore)
    }

    @Provides
    @Singleton
    fun provideSessionDataStore(@ApplicationContext context: Context): SessionDataStore {
        return SessionDataStoreImpl(context)
    }

    @Provides
    @Singleton
    fun provideSessionStateHolder(sessionDataStore: SessionDataStore): SessionStateHolder {
        return SessionStateHolderImpl(sessionDataStore = sessionDataStore)
    }

    @Provides
    @Singleton
    fun provideTokenRefresher(
        authRepository: Provider<AuthRepository>,
        applicationStateHolder: ApplicationStateHolder,
    ): TokenRefresher {
        return TokenRefresherImpl(
            authRepository = authRepository,
            applicationStateHolder = applicationStateHolder
        )
    }

    @Provides
    @Singleton
    fun provideAuthGraphQLService(apolloClient: ApolloClient): AuthGraphQLService {
        return AuthGraphQLServiceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authGraphQLService: AuthGraphQLService,
        applicationStateHolder: ApplicationStateHolder
    ): AuthRepository {
        return AuthRepositoryImpl(
            authGraphQLService = authGraphQLService,
            applicationStateHolder = applicationStateHolder
        )
    }
}