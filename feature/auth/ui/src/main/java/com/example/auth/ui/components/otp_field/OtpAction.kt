package com.example.auth.ui.components.otp_field

sealed interface OtpAction {
    data class OnEnterNumber(val number: Int? = null, val index: Int) : OtpAction
    data class OnChangeFieldFocused(val index: Int) : OtpAction
    data object OnKeyboardBack : OtpAction
}