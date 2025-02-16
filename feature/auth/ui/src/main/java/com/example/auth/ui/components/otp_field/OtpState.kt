package com.example.auth.ui.components.otp_field

const val OTP_LENGTH = 6

data class OtpState(
    val code: List<Int?> = (1..OTP_LENGTH).map { null },
    val focusedIndex: Int? = null,
)