package com.example.account.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

enum class AddressAlias{
    HOME, WORK, HOTEL, OTHERS
}

enum class Gender{
    MALE, FEMALE, OTHERS, UNDISCLOSED
}

data class DeliveryAddress(
    val id: String,
    val receiverName: String,
    val receiverPhone: String,
    val addressAlias: AddressAlias,
    val otherAlias: String?,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val nearbyLandmark: String?,
    val deliveryInstruction: String?,
    val authId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class Profile(
    val id:String,
    val name:String,
    val imageUrl:String?,
    val dob: LocalDate?,
    val anniversary: LocalDate?,
    val gender: Gender?,
    val authId: String,
)

data class CreateProfileInput(
    val name: String,
    val imageUrl: String?,
    val dob: LocalDate?,
    val anniversary: LocalDate?,
    val gender: Gender,
    val authId: String
)