package com.example.auth.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.example.schema.RefreshAccessTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.schema.type.AuthRole
import com.example.schema.type.SignInWithEmailInput
import com.example.schema.type.SignInWithGoogleInput
import com.example.schema.type.SignInWithPhoneInput

interface AuthGraphQLService {

    suspend fun signInWithEmail(
        email: String,
        authRole: AuthRole,
        otp: String? = null
    ): ApolloResponse<SignInWithEmailMutation.Data>

    suspend fun signInWithPhone(
        phone: String,
        authRole: AuthRole,
        otp: String? = null
    ): ApolloResponse<SignInWithPhoneMutation.Data>

    suspend fun signInWithGoogle(
        idToken: String,
        authRole: AuthRole,
    ): ApolloResponse<SignInWithGoogleMutation.Data>

    suspend fun refreshAccessToken(refreshToken: String): ApolloResponse<RefreshAccessTokenMutation.Data>
}

class AuthGraphQLServiceImpl(private val apolloClient: ApolloClient) : AuthGraphQLService {
    override suspend fun signInWithEmail(
        email: String,
        authRole: AuthRole,
        otp: String?
    ): ApolloResponse<SignInWithEmailMutation.Data> {
        val optionalOtp = Optional.presentIfNotNull(otp)
        val response = apolloClient.mutation(
            SignInWithEmailMutation(
                SignInWithEmailInput(
                    email = email,
                    auth_role = authRole,
                    otp = optionalOtp
                )
            )
        ).execute()

        return response
    }

    override suspend fun signInWithPhone(
        phone: String,
        authRole: AuthRole,
        otp: String?
    ): ApolloResponse<SignInWithPhoneMutation.Data> {
        val optionalOtp = Optional.presentIfNotNull(otp)
        val response = apolloClient.mutation(
            SignInWithPhoneMutation(
                SignInWithPhoneInput(
                    phone = phone,
                    auth_role = authRole,
                    otp = optionalOtp
                )
            )
        ).execute()
        return response
    }

    override suspend fun signInWithGoogle(
        idToken: String,
        authRole: AuthRole
    ) = apolloClient.mutation(
        SignInWithGoogleMutation(
            SignInWithGoogleInput(
                id_token = idToken,
                auth_role = authRole,
            )
        )
    ).execute()

    override suspend fun refreshAccessToken(refreshToken: String) = apolloClient
        .mutation(
            RefreshAccessTokenMutation(refreshToken)
        ).execute()
}