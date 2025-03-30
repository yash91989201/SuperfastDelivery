package com.example.account.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.example.account.data.mappers.toSchema
import com.example.account.domain.model.CreateDeliveryAddressInput
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.UpdateProfileInput
import com.example.schema.CreateDeliveryAddressMutation
import com.example.schema.CreateProfileMutation
import com.example.schema.DeleteDeliveryAddressMutation
import com.example.schema.GetDefaultDeliveryAddressQuery
import com.example.schema.ListDeliveryAddressQuery
import com.example.schema.UpdateDefaultDeliveryAddressMutation
import com.example.schema.UpdateProfileMutation

interface AccountGraphQLService {
    suspend fun createProfile(
        newProfile: CreateProfileInput
    ): ApolloResponse<CreateProfileMutation.Data>

    suspend fun updateProfile(updatedProfile: UpdateProfileInput): ApolloResponse<UpdateProfileMutation.Data>

    suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput): ApolloResponse<CreateDeliveryAddressMutation.Data>

    suspend fun getDefaultDeliveryAddress(): ApolloResponse<GetDefaultDeliveryAddressQuery.Data>

    suspend fun listDeliveryAddresses(): ApolloResponse<ListDeliveryAddressQuery.Data>

    suspend fun updateDefaultDeliveryAddress(deliveryAddressId: String): ApolloResponse<UpdateDefaultDeliveryAddressMutation.Data>

    suspend fun deleteDeliveryAddress(addressId: String): ApolloResponse<DeleteDeliveryAddressMutation.Data>
}

class AccountGraphQLServiceImpl(private val apolloClient: ApolloClient) : AccountGraphQLService {
    override suspend fun createProfile(newProfile: CreateProfileInput) = apolloClient
        .mutation(CreateProfileMutation(newProfile.toSchema()))
        .execute()

    override suspend fun updateProfile(updatedProfile: UpdateProfileInput) = apolloClient
        .mutation(UpdateProfileMutation(updatedProfile.toSchema()))
        .execute()

    override suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput) =
        apolloClient
            .mutation(CreateDeliveryAddressMutation(newDeliveryAddress.toSchema()))
            .execute()

    override suspend fun getDefaultDeliveryAddress() = apolloClient
        .query(GetDefaultDeliveryAddressQuery())
        .execute()

    override suspend fun listDeliveryAddresses() = apolloClient
        .query(ListDeliveryAddressQuery())
        .execute()

    override suspend fun updateDefaultDeliveryAddress(
        deliveryAddressId: String,
    ): ApolloResponse<UpdateDefaultDeliveryAddressMutation.Data> = apolloClient.mutation(
        UpdateDefaultDeliveryAddressMutation(deliveryAddressId)
    ).execute()

    override suspend fun deleteDeliveryAddress(addressId: String) = apolloClient.mutation(
        DeleteDeliveryAddressMutation(
            addressId = addressId
        )
    ).execute()
}