package com.example.auth.data.repository

import com.example.auth.data.mappers.toDomain
import com.example.auth.data.remote.AuthGraphQLService
import com.example.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authGraphqlService: AuthGraphQLService) : AuthRepository {
    override suspend fun signInWithEmail(
        email: String,
        otp: String?
    ) = runCatching {
        val response = authGraphqlService.signInWithEmail(email, otp)
        response.exception?.let { throw Exception(it.toString()) }
        response.data?.SignInWithEmail?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun signInWithPhone(
        phone: String,
        otp: String?
    ) = runCatching {
        val response = authGraphqlService.signInWithPhone(phone, otp)
        response.exception?.let { throw Exception(it.toString()) }
        response.data?.SignInWithPhone?.toDomain() ?: throw Exception("No Data returned")
    }

    override suspend fun signInWithGoogle(idToken: String) = runCatching {
        val response = authGraphqlService.signInWithGoogle(idToken = idToken)
        response.exception?.let { throw Exception(it.toString()) }
        response.data?.SignInWithGoogle?.toDomain() ?: throw Exception("No Data returned")
    }

    override suspend fun refreshToken(sessionId: String) = runCatching {
        val response = authGraphqlService.refreshToken(sessionId = sessionId)
        response.exception?.let { throw Exception(it.toString()) }
        response.data?.RefreshToken?.toDomain() ?: throw Exception("No Data returned")
    }
}