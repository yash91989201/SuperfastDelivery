package com.example.auth.domain.repository

import com.example.auth.domain.model.AuthRole
import com.example.auth.domain.model.SignInResponse

interface AuthRepository {
    suspend fun signInWithEmail(
        email: String,
        authRole: AuthRole,
        otp: String? = null
    ): Result<SignInResponse>

    suspend fun signInWithPhone(
        phone: String,
        authRole: AuthRole,
        otp: String? = null
    ): Result<SignInResponse>

    suspend fun signInWithGoogle(idToken: String, authRole: AuthRole): Result<SignInResponse>
    suspend fun refreshAccessToken(refreshToken: String): Result<SignInResponse>
}