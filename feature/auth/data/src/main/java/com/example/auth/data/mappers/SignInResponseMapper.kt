package com.example.auth.data.mappers

import com.example.auth.domain.model.SignInResponse
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.RefreshTokenMutation

fun SignInWithEmailMutation.SignInWithEmail.toDomain(): SignInResponse = SignInResponse(
    auth = this.auth?.toDomain(),
    profile = this.profile?.toDomain(),
    verityOtp = this.verify_otp,
    createProfile = this.create_profile,
    sessionId = this.session_id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)

fun SignInWithPhoneMutation.SignInWithPhone.toDomain(): SignInResponse = SignInResponse(
    auth = this.auth?.toDomain(),
    profile = this.profile?.toDomain(),
    verityOtp = this.verify_otp,
    createProfile = this.create_profile,
    sessionId = this.session_id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)

fun SignInWithGoogleMutation.SignInWithGoogle.toDomain(): SignInResponse = SignInResponse(
    auth = this.auth?.toDomain(),
    profile = this.profile?.toDomain(),
    verityOtp = false,
    createProfile = false,
    sessionId = this.session_id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)

fun RefreshTokenMutation.RefreshToken.toDomain(): SignInResponse = SignInResponse(
    auth = this.auth?.toDomain(),
    profile = this.profile?.toDomain(),
    verityOtp = false,
    createProfile = false,
    sessionId = this.session_id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)
