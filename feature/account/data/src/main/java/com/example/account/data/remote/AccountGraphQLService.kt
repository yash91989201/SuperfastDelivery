package com.example.account.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.example.account.data.mappers.fromDomain
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.schema.CreateProfileMutation
import com.example.schema.UpdateProfileMutation

interface AccountGraphQLService{
    suspend fun createProfile(
      newProfile:CreateProfileInput
    ):ApolloResponse<CreateProfileMutation.Data>
    suspend fun updateProfile(profile: Profile):ApolloResponse<UpdateProfileMutation.Data>
}

class AccountGraphQLServiceImpl(private val apolloClient: ApolloClient):AccountGraphQLService{
    override suspend fun createProfile(newProfile: CreateProfileInput): ApolloResponse<CreateProfileMutation.Data> {
        val response = apolloClient.mutation(
            CreateProfileMutation(
                com.example.schema.type.CreateProfileInput(
                    name = newProfile.name,
                    image_url = Optional.presentIfNotNull(newProfile.imageUrl),
                    dob = Optional.presentIfNotNull(newProfile.dob),
                    anniversary = Optional.presentIfNotNull(newProfile.anniversary),
                    gender = Optional.presentIfNotNull(newProfile.gender.fromDomain()),
                    auth_id = newProfile.authId
                )
            )
        ).execute()

        return response
    }

    override suspend fun updateProfile(profile: Profile): ApolloResponse<UpdateProfileMutation.Data> {
        TODO("Not yet implemented")
    }
}