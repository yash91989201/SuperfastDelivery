package com.example.core.app_state.models

import java.time.LocalDate

enum class Gender {
    MALE, FEMALE, OTHERS, UNDISCLOSED
}

data class Profile(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val dob: LocalDate?,
    val anniversary: LocalDate?,
    val gender: Gender?,
    val authId: String,
)