package com.example.auth.data.repository

import android.util.Log
import com.example.auth.data.mappers.toDomain
import com.example.auth.data.remote.AuthGraphQLService
import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authGraphqlService: AuthGraphQLService) : AuthRepository {
    override suspend fun signInWithEmail(
        email: String,
        otp: String?
    ): Result<SignInResponse> {
        return try {
            val response = authGraphqlService.signInWithEmail(email, otp)
            if (response.exception == null) {
                response.data?.SignInWithEmail?.let {
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

    override suspend fun signInWithPhone(
        phone: String,
        otp: String?
    ): Result<SignInResponse> {
        return try {
            val response = authGraphqlService.signInWithPhone(phone, otp)
            if (response.exception == null) {
                response.data?.SignInWithPhone?.let {
                    Result.success(it.toDomain())
                } ?: run {
                    Result.failure(Exception("Error Occurred: Fetching recipes"))
                }
            } else {
                Result.failure(Exception("Error Occurred: Fetching recipes"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<SignInResponse> {
        return try {
            val response = authGraphqlService.signInWithGoogle(idToken = idToken)
            if (response.exception == null) {
                response.data?.SignInWithGoogle?.let {
                    Result.success(it.toDomain())
                } ?: run {
                    Result.failure(Exception("Error Occurred: Fetching recipes"))
                }
            } else {
                Result.failure(Exception("Error Occurred: Fetching recipes"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}