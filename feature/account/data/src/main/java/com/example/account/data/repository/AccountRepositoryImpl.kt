package com.example.account.data.repository

import android.util.Log
import com.example.account.data.mappers.toDomain
import com.example.account.data.remote.AccountGraphQLService
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.account.domain.repository.AccountRepository

class AccountRepositoryImpl(private val accountGraphQLService: AccountGraphQLService) :
    AccountRepository {
    override suspend fun createProfile(newProfile: CreateProfileInput): Result<Profile> {
        return try {
            val response = accountGraphQLService.createProfile(newProfile)
            if (response.exception == null) {
                response.data?.CreateProfile?.let {
                    Result.success(it.toDomain())
                } ?: run {
                    Result.failure(Exception("No data returned"))
                }
            } else {
                Result.failure(Exception(response.exception.toString()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(profile: Profile): Result<Profile> {
        TODO("Not yet implemented")
    }
}