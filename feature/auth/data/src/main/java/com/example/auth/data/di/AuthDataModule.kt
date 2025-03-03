package com.example.auth.data.di

import com.apollographql.apollo.ApolloClient
import com.example.auth.data.remote.AuthGraphQLService
import com.example.auth.data.remote.AuthGraphQLServiceImpl
import com.example.auth.data.repository.AuthRepositoryImpl
import com.example.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Provides
    @Singleton
    fun provideAuthGraphQLService(apolloClient: ApolloClient): AuthGraphQLService{
        return AuthGraphQLServiceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authGraphQLService: AuthGraphQLService): AuthRepository{
        return AuthRepositoryImpl(authGraphQLService)
    }
}