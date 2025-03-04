package com.example.auth.data.mappers

import com.example.auth.domain.model.Auth
import com.example.auth.domain.model.Profile
import com.example.auth.domain.model.SignInResponse
import com.example.schema.RefreshTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.schema.type.AuthRole
import com.example.schema.type.Gender

fun AuthRole.toDomain(): com.example.auth.domain.model.AuthRole {
    return when (this) {
        AuthRole.CUSTOMER -> com.example.auth.domain.model.AuthRole.CUSTOMER
        AuthRole.DELIVERY_PARTNER -> com.example.auth.domain.model.AuthRole.DELIVERY_PARTNER
        AuthRole.VENDOR -> com.example.auth.domain.model.AuthRole.VENDOR
        AuthRole.ADMIN -> com.example.auth.domain.model.AuthRole.ADMIN
        AuthRole.UNKNOWN__ -> com.example.auth.domain.model.AuthRole.CUSTOMER // Default to CUSTOMER
    }
}

fun Gender.toDomain(): com.example.auth.domain.model.Gender {
    return when (this) {
        Gender.MALE -> com.example.auth.domain.model.Gender.MALE
        Gender.FEMALE -> com.example.auth.domain.model.Gender.FEMALE
        Gender.OTHERS -> com.example.auth.domain.model.Gender.OTHERS
        Gender.UNDISCLOSED -> com.example.auth.domain.model.Gender.UNDISCLOSED
        Gender.UNKNOWN__ -> TODO()
    }
}

fun SignInWithEmailMutation.Auth.toDomain(): Auth {
    return Auth(
        id = this.id,
        email = this.email,
        emailVerified = this.email_verified,
        phone = this.phone,
        authRole = this.auth_role.toDomain(),
    )
}

fun SignInWithEmailMutation.Profile.toDomain(): Profile {
    return Profile(
        id = this.id,
        name = this.name,
        imageUrl = this.image_url,
        dob = this.dob,
        anniversary = this.anniversary,
        gender = this.gender?.toDomain(),
        authId = this.auth_id,
    )
}

fun SignInWithEmailMutation.SignInWithEmail.toDomain(): SignInResponse {
    return SignInResponse(
        auth = this.auth?.toDomain(),
        profile = this.profile?.toDomain(),
        verityOtp = this.verify_otp,
        createProfile = this.create_profile,
        sessionId = this.session_id,
        accessToken = this.access_token,
        accessTokenExpiresAt = this.access_token_expires_at,
    )
}

fun SignInWithPhoneMutation.Auth.toDomain(): Auth {
    return Auth(
        id = this.id,
        email = this.email,
        emailVerified = this.email_verified,
        phone = this.phone,
        authRole = this.auth_role.toDomain(),
    )
}

fun SignInWithPhoneMutation.Profile.toDomain(): Profile {
    return Profile(
        id = this.id,
        name = this.name,
        imageUrl = this.image_url,
        dob = this.dob,
        anniversary = this.anniversary,
        gender = this.gender?.toDomain(),
        authId = this.auth_id,
    )
}

fun SignInWithPhoneMutation.SignInWithPhone.toDomain(): SignInResponse {
    return SignInResponse(
        auth = this.auth?.toDomain(),
        profile = this.profile?.toDomain(),
        verityOtp = this.verify_otp,
        createProfile = this.create_profile,
        sessionId = this.session_id,
        accessToken = this.access_token,
        accessTokenExpiresAt = this.access_token_expires_at,
    )
}

fun SignInWithGoogleMutation.Auth.toDomain(): Auth {
    return Auth(
        id = this.id,
        email = this.email,
        emailVerified = this.email_verified,
        phone = this.phone,
        authRole = this.auth_role.toDomain()
    )
}

fun SignInWithGoogleMutation.Profile.toDomain(): Profile {
    return Profile(
        id = this.id,
        name = this.name,
        imageUrl = this.image_url,
        dob = this.dob,
        anniversary = this.anniversary,
        gender = this.gender?.toDomain(),
        authId = this.auth_id,
    )
}

fun SignInWithGoogleMutation.SignInWithGoogle.toDomain(): SignInResponse {
    return SignInResponse(
        auth = this.auth?.toDomain(),
        profile = this.profile?.toDomain(),
        verityOtp = false,
        createProfile = false,
        sessionId = this.session_id,
        accessToken = this.access_token,
        accessTokenExpiresAt = this.access_token_expires_at,
    )
}

fun RefreshTokenMutation.Auth.toDomain(): Auth {
    return Auth(
        id = this.id,
        email = this.email,
        emailVerified = this.email_verified,
        phone = this.phone,
        authRole = this.auth_role.toDomain(),
    )
}

fun RefreshTokenMutation.Profile.toDomain(): Profile {
    return Profile(
        id = this.id,
        name = this.name,
        imageUrl = this.image_url,
        dob = this.dob,
        anniversary = this.anniversary,
        gender = this.gender?.toDomain(),
        authId = this.auth_id,
    )
}

fun RefreshTokenMutation.RefreshToken.toDomain(): SignInResponse {
    return SignInResponse(
        auth = this.auth?.toDomain(),
        profile = this.profile?.toDomain(),
        verityOtp = false,
        createProfile = false,
        sessionId = this.session_id,
        accessToken = this.access_token,
        accessTokenExpiresAt = this.access_token_expires_at,
    )
}