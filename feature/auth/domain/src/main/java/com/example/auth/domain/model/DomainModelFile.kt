package com.example.auth.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

enum class AuthRole{
    CUSTOMER, DELIVERY_PARTNER, VENDOR, ADMIN
}

enum class Gender{
    MALE, FEMALE, OTHERS, UNDISCLOSED
}

data class Auth(
    val id:String,
    val email:String?,
    val emailVerified:Boolean,
    val phone:String?,
    val role: AuthRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
)

data class Profile(
    val id:String,
    val name:String,
    val imageUrl:String?,
    val dob: LocalDate?,
    val anniversary: LocalDate?,
    val gender: Gender?,
    val authId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class SignInResponse(
    val auth:Auth?,
    val profile:Profile?,
    val verityOtp:Boolean,
)