package com.example.account.domain.model

import java.time.LocalDate

enum class AddressAlias {
    HOME, WORK, HOTEL, OTHERS
}

enum class Gender {
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
    val isDefault: Boolean,
    val authId: String,
)

data class DefaultDeliveryAddress(
    val address: String,
    val addressAlias: AddressAlias,
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

data class CreateProfileInput(
    val name: String,
    val imageUrl: String?,
    val dob: LocalDate?,
    val anniversary: LocalDate?,
    val gender: Gender?,
    val authId: String
)

data class UpdateProfileInput(
    val id: String,
    val name: String?,
    val imageUrl: String?,
    val dob: LocalDate?,
    val anniversary: LocalDate?,
    val gender: Gender,
    val authId: String
)

data class CreateDeliveryAddressInput(
    val receiverName: String,
    val receiverPhone: String,
    val addressAlias: AddressAlias,
    val otherAlias: String?,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val nearbyLandmark: String?,
    val deliveryInstruction: String?,
    val isDefault: Boolean,
    val authId: String,
)

data class ListDeliveryAddress(
    val deliveryAddress: List<DeliveryAddress>,
)