package com.example.auth.data.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.example.auth.data.data_store_manager.SessionDataStoreManager
import com.example.auth.data.remote.AuthGraphQLService
import com.example.auth.data.remote.AuthGraphQLServiceImpl
import com.example.auth.data.repository.AuthRepositoryImpl
import com.example.auth.data.session_state_holder.SessionStateHolderImpl
import com.example.auth.domain.repository.AuthRepository
import com.example.auth.domain.session_state_holder.SessionStateHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Provides
    @Singleton
    fun provideAuthGraphQLService(apolloClient: ApolloClient): AuthGraphQLService {
        return AuthGraphQLServiceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authGraphQLService: AuthGraphQLService): AuthRepository {
        return AuthRepositoryImpl(authGraphQLService)
    }

    @Provides
    @Singleton
    fun provideSessionDataStoreManager(@ApplicationContext context: Context): SessionDataStoreManager {
        return SessionDataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideSessionStateHolder(sessionDataStoreManager: SessionDataStoreManager): SessionStateHolder {
        return SessionStateHolderImpl(sessionDataStoreManager = sessionDataStoreManager)
    }
}