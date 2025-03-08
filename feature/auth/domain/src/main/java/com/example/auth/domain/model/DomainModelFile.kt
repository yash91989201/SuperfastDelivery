package com.example.auth.domain.model

import java.time.Instant
import java.time.LocalDate

enum class AuthRole {
    CUSTOMER, DELIVERY_PARTNER, VENDOR, ADMIN
}

enum class Gender {
    MALE, FEMALE, OTHERS, UNDISCLOSED
}

data class Auth(
    val id: String,
    val email: String?,
    val emailVerified: Boolean,
    val phone: String?,
    val authRole: AuthRole,
)

data class Session(
    val id: String,
    val accessToken: String,
    val accessTokenExpiresAt: Instant?
)

data class Profile(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val dob: LocalDate?,
    val anniversary: LocalDate?,
    val gender: Gender?,
    val authId: String,
)

data class SignInResponse(
    val auth: Auth?,
    val profile: Profile?,
    val session: Session?,
    val createProfile: Boolean,
    val verityOtp: Boolean,
)