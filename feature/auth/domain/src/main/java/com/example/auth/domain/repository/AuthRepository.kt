package com.example.auth.domain.repository

import com.example.auth.domain.model.SignInResponse

interface AuthRepository {
    suspend fun signInWithEmail(email: String, otp: String? = null): Result<SignInResponse>
    suspend fun signInWithPhone(phone: String, otp: String? = null): Result<SignInResponse>
    suspend fun signInWithGoogle(idToken: String): Result<SignInResponse>
    suspend fun refreshToken(sessionId: String): Result<SignInResponse>
}