package com.example.search.data.di

import com.apollographql.apollo.ApolloClient
import com.example.search.data.remote.SearchGraphQLService
import com.example.search.data.remote.SearchGraphQLServiceImpl
import com.example.search.data.repository.SearchRepositoryImpl
import com.example.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchDataModule {

    @Provides
    @Singleton
    fun provideSearchGraphQLService(apolloClient: ApolloClient): SearchGraphQLService {
        return SearchGraphQLServiceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchGraphQLService: SearchGraphQLService): SearchRepository {
        return SearchRepositoryImpl(searchGraphQLService)
    }
}