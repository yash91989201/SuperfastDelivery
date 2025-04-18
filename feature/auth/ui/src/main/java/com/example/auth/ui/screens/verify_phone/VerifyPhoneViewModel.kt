package com.example.auth.ui.screens.verify_phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.use_cases.SignInWithPhoneUseCase
import com.example.core.navigation.NavigationSubGraphDest
import com.example.core.navigation.Navigator
import com.example.core.utils.NetworkResult
import com.example.core.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VerifyPhoneViewModel @Inject constructor(
    private val navigator: Navigator,
    private val signInWithPhoneUseCase: SignInWithPhoneUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(VerifyPhone.UiState())
    val uiState: StateFlow<VerifyPhone.UiState> get() = _uiState.asStateFlow()

    fun onEvent(event: VerifyPhone.Event) {
        when (event) {
            VerifyPhone.Event.GoBack -> {
                navigator.navigateBack()
            }

            VerifyPhone.Event.GoToSearchHomeScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.SearchHome)
            }

            VerifyPhone.Event.GoToAccountCreateProfileScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountCreateProfile)
            }

            is VerifyPhone.Event.VerifyPhone -> {
                verifyPhone(event.phone, event.otp)
            }

            is VerifyPhone.Event.ResendOtp -> {
                resendOtp(email = event.phone)
            }
        }
    }

    private fun verifyPhone(email: String, otp: String) {
        signInWithPhoneUseCase.invoke(email, otp).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isVerifyingOtp = true) }
                }

                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isVerifyingOtp = false) }

                    result.data?.let { signInRes ->
                        if (signInRes.createProfile) {
                            onEvent(VerifyPhone.Event.GoToAccountCreateProfileScreen)
                        } else {
                            onEvent(VerifyPhone.Event.GoToSearchHomeScreen)
                        }
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isVerifyingOtp = false,
                            verifyOtpError = UiText.RemoteString(
                                result.message ?: "Unknown Error"
                            )
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun resendOtp(email: String) {
        signInWithPhoneUseCase(phone = email).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update {
                        it.copy(
                            isResendingOtp = true
                        )
                    }
                }

                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isResendingOtp = false,
                            data = result.data
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isResendingOtp = false,
                            resendOtpError = UiText.RemoteString(
                                result.message ?: "Unknown Error"
                            )
                        )
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}

object VerifyPhone {
    data class UiState(
        val isVerifyingOtp: Boolean = false,
        val isResendingOtp: Boolean = false,
        val verifyOtpError: UiText = UiText.Idle,
        val resendOtpError: UiText = UiText.Idle,
        val data: SignInResponse? = null
    )

    sealed interface Event {
        data class ResendOtp(val phone: String) : Event
        data class VerifyPhone(val phone: String, val otp: String) : Event

        // navigation event
        data object GoBack : Event
        data object GoToSearchHomeScreen : Event
        data object GoToAccountCreateProfileScreen : Event
    }
}