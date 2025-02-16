package com.example.auth.data.mappers

import com.example.auth.domain.model.SignInResponse
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.schema.SignInWithGoogleMutation
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


fun SignInWithEmailMutation.Auth.toDomain(): com.example.auth.domain.model.Auth {
    return com.example.auth.domain.model.Auth(
        id = this.id,
        email = this.email,
        emailVerified = this.email_verified,
        phone = this.phone,
        role = this.role.toDomain(),
    )
}


fun SignInWithEmailMutation.Profile.toDomain(): com.example.auth.domain.model.Profile {
    return com.example.auth.domain.model.Profile(
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
        createProfile = this.create_profile
    )
}

fun SignInWithPhoneMutation.Auth.toDomain(): com.example.auth.domain.model.Auth {
    return com.example.auth.domain.model.Auth(
        id = this.id,
        email = this.email,
        emailVerified = this.email_verified,
        phone = this.phone,
        role = this.role.toDomain(),
    )
}


fun SignInWithPhoneMutation.Profile.toDomain(): com.example.auth.domain.model.Profile {
    return com.example.auth.domain.model.Profile(
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
        createProfile = this.create_profile
    )
}

fun SignInWithGoogleMutation.Auth.toDomain(): com.example.auth.domain.model.Auth {
    return com.example.auth.domain.model.Auth(
        id = this.id,
        email = this.email,
        emailVerified = this.email_verified,
        phone = this.phone,
        role = this.role.toDomain()
    )
}


fun SignInWithGoogleMutation.Profile.toDomain(): com.example.auth.domain.model.Profile {
    return com.example.auth.domain.model.Profile(
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
    )
}