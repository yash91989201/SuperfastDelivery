package com.example.auth.data.repository

import com.example.auth.data.mappers.toDomain
import com.example.auth.data.mappers.toSchema
import com.example.auth.data.mappers.toStore
import com.example.auth.data.remote.AuthGraphQLService
import com.example.auth.domain.model.AuthRole
import com.example.auth.domain.repository.AuthRepository
import com.example.core.app_state.state_holder.ApplicationStateHolder

class AuthRepositoryImpl(
    private val authGraphQLService: AuthGraphQLService,
    private val applicationStateHolder: ApplicationStateHolder,
) : AuthRepository {
    override suspend fun signInWithEmail(
        email: String,
        authRole: AuthRole,
        otp: String?
    ) = runCatching {
        val response = authGraphQLService.signInWithEmail(
            email = email,
            authRole = authRole.toSchema(),
            otp = otp
        )

        response.exception?.let { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.let { throw Exception(it) }

        val signInRes = response.data?.SignInWithEmail

        signInRes?.session?.let {
            applicationStateHolder.sessionStateHolder.updateSession(it.toStore())
        }

        signInRes?.auth?.let {
            applicationStateHolder.authStateHolder.updateAuth(it.toStore())
        }

        signInRes?.profile?.let {
            applicationStateHolder.profileStateHolder.updateProfile(it.toStore())
        }

        signInRes?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun signInWithPhone(
        phone: String,
        authRole: AuthRole,
        otp: String?
    ) = runCatching {
        val response = authGraphQLService.signInWithPhone(
            phone = phone,
            authRole = authRole.toSchema(),
            otp = otp
        )

        response.exception?.let { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.let { throw Exception(it) }

        val signInRes = response.data?.SignInWithPhone

        signInRes?.session?.let {
            applicationStateHolder.sessionStateHolder.updateSession(it.toStore())
        }

        signInRes?.auth?.let {
            applicationStateHolder.authStateHolder.updateAuth(it.toStore())
        }

        signInRes?.profile?.let {
            applicationStateHolder.profileStateHolder.updateProfile(it.toStore())
        }

        signInRes?.toDomain() ?: throw Exception("No data returned")
    }


    override suspend fun signInWithGoogle(idToken: String, authRole: AuthRole) = runCatching {
        val response = authGraphQLService.signInWithGoogle(
            idToken = idToken,
            authRole = authRole.toSchema(),
        )

        response.exception?.let { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.let { throw Exception(it) }

        val signInRes = response.data?.SignInWithGoogle

        signInRes?.session?.let {
            applicationStateHolder.sessionStateHolder.updateSession(it.toStore())
        }

        signInRes?.auth?.let {
            applicationStateHolder.authStateHolder.updateAuth(it.toStore())
        }

        signInRes?.profile?.let {
            applicationStateHolder.profileStateHolder.updateProfile(it.toStore())
        }

        signInRes?.toDomain() ?: throw Exception("No data returned")
    }

    override suspend fun refreshAccessToken(refreshToken: String) = runCatching {
        val response = authGraphQLService.refreshAccessToken(refreshToken)

        response.exception?.let { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.let {
            applicationStateHolder.sessionStateHolder.clearSession()
            applicationStateHolder.authStateHolder.clearAuth()
            applicationStateHolder.profileStateHolder.clearProfile()

            throw Exception(it)
        }

        val refreshAccessTokenRes = response.data?.RefreshAccessToken

        refreshAccessTokenRes?.session?.let {
            applicationStateHolder.sessionStateHolder.updateSession(it.toStore())
        }

        refreshAccessTokenRes?.auth?.let {
            applicationStateHolder.authStateHolder.updateAuth(it.toStore())
        }

        refreshAccessTokenRes?.profile?.let {
            applicationStateHolder.profileStateHolder.updateProfile(it.toStore())
        }

        response.data?.RefreshAccessToken?.toDomain() ?: throw Exception("No Data returned")
    }
}