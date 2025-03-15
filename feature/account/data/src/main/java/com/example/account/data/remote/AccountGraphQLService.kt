package com.example.account.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.example.account.data.mappers.toSchema
import com.example.account.domain.model.CreateDeliveryAddressInput
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.schema.CreateDeliveryAddressMutation
import com.example.schema.CreateProfileMutation
import com.example.schema.ListDeliveryAddressQuery
import com.example.schema.UpdateProfileMutation

interface AccountGraphQLService {
    suspend fun createProfile(
        newProfile: CreateProfileInput
    ): ApolloResponse<CreateProfileMutation.Data>

    suspend fun updateProfile(profile: Profile): ApolloResponse<UpdateProfileMutation.Data>

    suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput): ApolloResponse<CreateDeliveryAddressMutation.Data>

    suspend fun listDeliveryAddresses(
        authId: String
    ): ApolloResponse<ListDeliveryAddressQuery.Data>
}

class AccountGraphQLServiceImpl(private val apolloClient: ApolloClient) : AccountGraphQLService {
    override suspend fun createProfile(newProfile: CreateProfileInput): ApolloResponse<CreateProfileMutation.Data> {
        val response = apolloClient.mutation(
            CreateProfileMutation(
                newProfile.toSchema()
            )
        ).execute()

        return response
    }

    override suspend fun updateProfile(profile: Profile): ApolloResponse<UpdateProfileMutation.Data> {
        TODO("Not yet implemented")
    }

    override suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput): ApolloResponse<CreateDeliveryAddressMutation.Data> {
        val response = apolloClient
            .mutation(
                CreateDeliveryAddressMutation(
                    newDeliveryAddress.toSchema()
                )
            )
            .execute()

        return response
    }

    override suspend fun listDeliveryAddresses(authId: String): ApolloResponse<ListDeliveryAddressQuery.Data> {
        val response = apolloClient
            .query(
                ListDeliveryAddressQuery(
                    authId = authId
                )
            )
            .execute()

        return response
    }
}