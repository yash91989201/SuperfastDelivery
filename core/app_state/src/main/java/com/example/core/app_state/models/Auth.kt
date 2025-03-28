package com.example.core.app_state.models

enum class AuthRole {
    CUSTOMER, DELIVERY_PARTNER, VENDOR, ADMIN
}

data class Auth(
    val id: String,
    val email: String?,
    val phone: String?,
    val emailVerified: Boolean,
    val authRole: AuthRole
)