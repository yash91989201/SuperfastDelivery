package com.example.account.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.example.account.data.mappers.toSchema
import com.example.account.domain.model.CreateDeliveryAddressInput
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.schema.CreateDeliveryAddressMutation
import com.example.schema.CreateProfileMutation
import com.example.schema.DeleteDeliveryAddressMutation
import com.example.schema.ListDeliveryAddressQuery
import com.example.schema.UpdateDefaultDeliveryAddressMutation
import com.example.schema.UpdateProfileMutation
import com.example.schema.type.UpdateDefaultDeliveryAddressInput

interface AccountGraphQLService {
    suspend fun createProfile(
        newProfile: CreateProfileInput
    ): ApolloResponse<CreateProfileMutation.Data>

    suspend fun updateProfile(profile: Profile): ApolloResponse<UpdateProfileMutation.Data>

    suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput): ApolloResponse<CreateDeliveryAddressMutation.Data>

    suspend fun listDeliveryAddresses(
        authId: String
    ): ApolloResponse<ListDeliveryAddressQuery.Data>

    suspend fun updateDefaultDeliveryAddress(
        deliveryAddressId: String,
        authId: String
    ): ApolloResponse<UpdateDefaultDeliveryAddressMutation.Data>

    suspend fun deleteDeliveryAddress(addressId: String): ApolloResponse<DeleteDeliveryAddressMutation.Data>
}

class AccountGraphQLServiceImpl(private val apolloClient: ApolloClient) : AccountGraphQLService {
    override suspend fun createProfile(newProfile: CreateProfileInput) = apolloClient
        .mutation(CreateProfileMutation(newProfile.toSchema()))
        .execute()

    override suspend fun updateProfile(profile: Profile): ApolloResponse<UpdateProfileMutation.Data> {
        TODO("Not yet implemented")
    }

    override suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput) =
        apolloClient
            .mutation(CreateDeliveryAddressMutation(newDeliveryAddress.toSchema()))
            .execute()

    override suspend fun listDeliveryAddresses(authId: String) = apolloClient
        .query(ListDeliveryAddressQuery(authId = authId))
        .execute()

    override suspend fun updateDefaultDeliveryAddress(
        deliveryAddressId: String,
        authId: String
    ): ApolloResponse<UpdateDefaultDeliveryAddressMutation.Data> = apolloClient.mutation(
        UpdateDefaultDeliveryAddressMutation(
            UpdateDefaultDeliveryAddressInput(
                delivery_address_id = deliveryAddressId,
                auth_id = authId
            )
        )
    ).execute()

    override suspend fun deleteDeliveryAddress(addressId: String) = apolloClient.mutation(
        DeleteDeliveryAddressMutation(
            addressId = addressId
        )
    ).execute()
}