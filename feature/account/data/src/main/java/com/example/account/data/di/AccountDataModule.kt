package com.example.account.data.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.example.account.data.data_store.ProfileDataStoreImpl
import com.example.account.data.remote.AccountGraphQLService
import com.example.account.data.remote.AccountGraphQLServiceImpl
import com.example.account.data.repository.AccountRepositoryImpl
import com.example.account.data.state_holder.ProfileStateHolderImpl
import com.example.account.domain.repository.AccountRepository
import com.example.common.data_store.ProfileDataStore
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.state_holder.ProfileStateHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountDataModule {

    @Provides
    @Singleton
    fun provideProfileDataStore(@ApplicationContext context: Context): ProfileDataStore {
        return ProfileDataStoreImpl(context)
    }

    @Provides
    @Singleton
    fun provideProfileStateHolder(profileDataStore: ProfileDataStore): ProfileStateHolder {
        return ProfileStateHolderImpl(profileDataStore)
    }

    @Provides
    @Singleton
    fun provideAccountGraphQLService(apolloClient: ApolloClient): AccountGraphQLService {
        return AccountGraphQLServiceImpl(apolloClient)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountGraphQLService: AccountGraphQLService,
        applicationStateHolder: ApplicationStateHolder
    ): AccountRepository {
        return AccountRepositoryImpl(accountGraphQLService, applicationStateHolder)
    }
}