package com.example.account.data.repository

import com.example.account.data.mappers.toDomain
import com.example.account.data.remote.AccountGraphQLService
import com.example.account.domain.model.CreateDeliveryAddressInput
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.account.domain.repository.AccountRepository

class AccountRepositoryImpl(private val accountGraphQLService: AccountGraphQLService) :
    AccountRepository {
    override suspend fun createProfile(newProfile: CreateProfileInput) = runCatching {
        val response = accountGraphQLService.createProfile(newProfile)

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        response.data?.CreateProfile?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun updateProfile(profile: Profile): Result<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput) =
        runCatching {
            val response = accountGraphQLService.createDeliveryAddress(newDeliveryAddress)

            response.exception?.also { throw Exception(it.toString()) }

            response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

            response.data?.CreateDeliveryAddress?.toDomain() ?: throw Exception("No data returned")
        }

    override suspend fun listDeliveryAddresses(authId: String) = runCatching {
        val response = accountGraphQLService.listDeliveryAddresses(authId)

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        response.data?.ListDeliveryAddress?.toDomain() ?: throw Exception("No data returned")
    }
}