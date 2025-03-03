package com.example.account.data.di


import com.apollographql.apollo.ApolloClient
import com.example.account.data.remote.AccountGraphQLService
import com.example.account.data.remote.AccountGraphQLServiceImpl
import com.example.account.data.repository.AccountRepositoryImpl
import com.example.account.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountDataModule {

    @Provides
    @Singleton
    fun provideAccountGraphQLService(apolloClient: ApolloClient):AccountGraphQLService{
        return AccountGraphQLServiceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(accountGraphQLService: AccountGraphQLService):AccountRepository{
        return AccountRepositoryImpl(accountGraphQLService)
    }
}