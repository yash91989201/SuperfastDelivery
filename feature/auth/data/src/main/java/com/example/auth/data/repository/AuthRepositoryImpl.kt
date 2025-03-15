package com.example.auth.data.repository

import com.example.auth.data.mappers.toDomain
import com.example.auth.data.mappers.toStore
import com.example.auth.data.remote.AuthGraphQLService
import com.example.auth.domain.repository.AuthRepository
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.models.Auth as StoreAuth
import com.example.common.models.Profile as StoreProfile
import com.example.common.models.Session as StoreSession

class AuthRepositoryImpl(
    private val authGraphQLService: AuthGraphQLService,
    private val applicationStateHolder: ApplicationStateHolder,
) : AuthRepository {
    override suspend fun signInWithEmail(
        email: String,
        otp: String?
    ) = runCatching {
        val response = authGraphQLService.signInWithEmail(email, otp)

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        val signInRes = response.data?.SignInWithEmail

        signInRes?.session?.let {
            applicationStateHolder.sessionStateHolder.updateSession(
                StoreSession(
                    id = it.id,
                    accessToken = it.access_token,
                    accessTokenExpiresAt = it.access_token_expires_at
                )
            )
        }

        signInRes?.auth?.let {
            applicationStateHolder.authStateHolder.updateAuth(
                StoreAuth(
                    id = it.id,
                    email = it.email,
                    phone = it.phone,
                    emailVerified = it.email_verified,
                    authRole = it.auth_role.toStore()
                )
            )
        }

        signInRes?.profile?.let {
            applicationStateHolder.profileStateHolder.updateProfile(
                StoreProfile(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.image_url,
                    dob = it.dob,
                    anniversary = it.anniversary,
                    gender = it.gender?.toStore(),
                    authId = it.auth_id
                )
            )
        }

        signInRes?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun signInWithPhone(
        phone: String,
        otp: String?
    ) = runCatching {
        val response = authGraphQLService.signInWithPhone(phone, otp)
        response.exception?.let { throw Exception(it.toString()) }
        response.errors?.firstOrNull()?.message?.let { throw Exception(it) }
        response.data?.SignInWithPhone?.toDomain() ?: throw Exception("No Data returned")
    }

    override suspend fun signInWithGoogle(idToken: String) = runCatching {
        val response = authGraphQLService.signInWithGoogle(idToken = idToken)
        response.exception?.let { throw Exception(it.toString()) }
        response.errors?.firstOrNull()?.message?.let { throw Exception(it) }
        response.data?.SignInWithGoogle?.toDomain() ?: throw Exception("No Data returned")
    }

    override suspend fun refreshToken(sessionId: String) = runCatching {
        val response = authGraphQLService.refreshToken(sessionId = sessionId)
        response.exception?.let { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.let { throw Exception(it) }


        response.data?.RefreshToken?.toDomain() ?: throw Exception("No Data returned")
    }
}