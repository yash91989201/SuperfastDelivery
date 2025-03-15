package com.example.account.domain.repository

import com.example.account.domain.model.CreateDeliveryAddressInput
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.DeliveryAddress
import com.example.account.domain.model.ListDeliveryAddress
import com.example.account.domain.model.Profile

interface AccountRepository {
    suspend fun createProfile(newProfile: CreateProfileInput): Result<Profile>
    suspend fun updateProfile(profile: Profile): Result<Profile>
    suspend fun createDeliveryAddress(newDeliveryAddress: CreateDeliveryAddressInput): Result<DeliveryAddress>
    suspend fun listDeliveryAddresses(authId: String): Result<ListDeliveryAddress>
}