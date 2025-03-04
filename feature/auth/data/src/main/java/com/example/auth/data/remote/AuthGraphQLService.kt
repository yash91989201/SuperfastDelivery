package com.example.auth.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.example.schema.RefreshTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.schema.type.SignInWithEmailInput
import com.example.schema.type.SignInWithGoogleInput
import com.example.schema.type.SignInWithPhoneInput

interface AuthGraphQLService {

    suspend fun signInWithEmail(
        email: String,
        otp: String? = null
    ): ApolloResponse<SignInWithEmailMutation.Data>

    suspend fun signInWithPhone(
        phone: String,
        otp: String? = null
    ): ApolloResponse<SignInWithPhoneMutation.Data>

    suspend fun signInWithGoogle(idToken: String): ApolloResponse<SignInWithGoogleMutation.Data>

    suspend fun refreshToken(sessionId: String): ApolloResponse<RefreshTokenMutation.Data>
}

class AuthGraphQLServiceImpl(private val apolloClient: ApolloClient) : AuthGraphQLService {
    override suspend fun signInWithEmail(
        email: String,
        otp: String?
    ): ApolloResponse<SignInWithEmailMutation.Data> {
        val optionalOtp = Optional.presentIfNotNull(otp)
        val response = apolloClient.mutation(
            SignInWithEmailMutation(
                SignInWithEmailInput(
                    email = email,
                    otp = optionalOtp
                )
            )
        ).execute()

        return response
    }

    override suspend fun signInWithPhone(
        phone: String,
        otp: String?
    ): ApolloResponse<SignInWithPhoneMutation.Data> {
        val optionalOtp = Optional.presentIfNotNull(otp)
        val response = apolloClient.mutation(
            SignInWithPhoneMutation(
                SignInWithPhoneInput(
                    phone = phone,
                    otp = optionalOtp
                )
            )
        ).execute()
        return response
    }

    override suspend fun signInWithGoogle(idToken: String): ApolloResponse<SignInWithGoogleMutation.Data> {
        val response = apolloClient.mutation(
            SignInWithGoogleMutation(
                SignInWithGoogleInput(
                    id_token = idToken
                )
            )
        ).execute()
        return response
    }

    override suspend fun refreshToken(sessionId: String): ApolloResponse<RefreshTokenMutation.Data> {
        val response = apolloClient.mutation(
            RefreshTokenMutation(sessionId = sessionId)
        ).execute()

        return response
    }

}