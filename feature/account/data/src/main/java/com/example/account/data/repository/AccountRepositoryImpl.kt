package com.example.account.data.repository

import com.example.account.data.mappers.toDomain
import com.example.account.data.mappers.toStore
import com.example.account.data.remote.AccountGraphQLService
import com.example.account.domain.model.CreateDeliveryAddressInput
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.UpdateProfileInput
import com.example.account.domain.repository.AccountRepository
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.app_state.models.Profile as StoreProfile

class AccountRepositoryImpl(
    private val accountGraphQLService: AccountGraphQLService,
    private val applicationStateHolder: ApplicationStateHolder,
) :
    AccountRepository {
    override suspend fun createProfile(newProfile: CreateProfileInput) = runCatching {
        val response = accountGraphQLService.createProfile(newProfile)

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        response.data?.CreateProfile?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun updateProfile(updatedProfile: UpdateProfileInput) = runCatching {
        val response = accountGraphQLService.updateProfile(updatedProfile)

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        val updateProfileRes = response.data?.UpdateProfile

        updateProfileRes?.let {
            applicationStateHolder.profileStateHolder.updateProfile(
                StoreProfile(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.image_url,
                    dob = it.dob,
                    anniversary = it.anniversary,
                    authId = it.auth_id,
                    gender = it.gender?.toStore(),
                )
            )
        }

        response.data?.UpdateProfile?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput) =
        runCatching {
            val response = accountGraphQLService.createDeliveryAddress(newDeliveryAddress)

            response.exception?.also { throw Exception(it.toString()) }

            response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

            response.data?.CreateDeliveryAddress?.toDomain() ?: throw Exception("No data returned")
        }

    override suspend fun getDefaultDeliveryAddress() = runCatching {
        val response = accountGraphQLService.getDefaultDeliveryAddress()

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        response.data?.GetDefaultDeliveryAddress?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun listDeliveryAddresses() = runCatching {
        val response = accountGraphQLService.listDeliveryAddresses()

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        response.data?.ListDeliveryAddress?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun updateDefaultDeliveryAddress(deliveryAddressId: String) =
        runCatching {
            val response =
                accountGraphQLService.updateDefaultDeliveryAddress(deliveryAddressId)

            response.exception?.also { throw Exception(it.toString()) }

            response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

            response.data?.UpdateDefaultDeliveryAddress?.message
                ?: throw Exception("No data returned")
        }

    override suspend fun deleteDeliveryAddress(addressId: String) = runCatching {
        val response = accountGraphQLService.deleteDeliveryAddress(addressId)

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        response.data?.DeleteDeliveryAddress?.message
            ?: throw Exception("No data returned")
    }
}