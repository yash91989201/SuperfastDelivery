package com.example.auth.data.mappers

import com.example.auth.domain.model.SignInResponse
import com.example.schema.RefreshAccessTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation

fun SignInWithEmailMutation.SignInWithEmail.toDomain() = SignInResponse(
    auth = this.auth?.toDomain(),
    session = this.session?.toDomain(),
    profile = this.profile?.toDomain(),
    verifyOtp = this.verify_otp,
    createProfile = this.create_profile,
)

fun SignInWithPhoneMutation.SignInWithPhone.toDomain() = SignInResponse(
    auth = this.auth?.toDomain(),
    profile = this.profile?.toDomain(),
    session = this.session?.toDomain(),
    verifyOtp = this.verify_otp,
    createProfile = this.create_profile,
)

fun SignInWithGoogleMutation.SignInWithGoogle.toDomain() = SignInResponse(
    auth = this.auth?.toDomain(),
    session = this.session?.toDomain(),
    profile = this.profile?.toDomain(),
    verifyOtp = false,
    createProfile = false
)

fun RefreshAccessTokenMutation.RefreshAccessToken.toDomain() = SignInResponse(
    auth = this.auth?.toDomain(),
    session = this.session?.toDomain(),
    profile = this.profile?.toDomain(),
    verifyOtp = false,
    createProfile = false
)
