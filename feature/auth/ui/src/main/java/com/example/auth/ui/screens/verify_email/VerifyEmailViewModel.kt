package com.example.auth.ui.screens.verify_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.use_cases.SignInWithEmailUseCase
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
class VerifyEmailViewModel @Inject constructor(
    private val navigator: Navigator,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyEmail.UiState())
    val uiState: StateFlow<VerifyEmail.UiState> get() = _uiState.asStateFlow()

    fun onEvent(event: VerifyEmail.Event) {
        when (event) {
            VerifyEmail.Event.GoBack -> {
                navigator.navigateBack()
            }

            VerifyEmail.Event.GoToSearchHomeScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.SearchHome)
            }

            VerifyEmail.Event.GoToAccountCreateProfileScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AccountCreateProfile)
            }

            is VerifyEmail.Event.VerifyEmail -> {
                verifyEmail(email = event.email, otp = event.otp)
            }

            is VerifyEmail.Event.ResendOtp -> {
                resendOtp(email = event.email)
            }
        }
    }

    private fun verifyEmail(email: String, otp: String) {
        signInWithEmailUseCase(email, otp).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isVerifyingOtp = true) }
                }

                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isVerifyingOtp = false) }

                    result.data?.let { signInRes ->
                        if (signInRes.createProfile) {
                            onEvent(VerifyEmail.Event.GoToAccountCreateProfileScreen)
                        }
                        onEvent(VerifyEmail.Event.GoToSearchHomeScreen)
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
        signInWithEmailUseCase(email = email).onEach { result ->
            when (result) {
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isResendingOtp = false,
                            resendOtpError = UiText.RemoteString(result.message ?: "Unknown Error")
                        )
                    }
                }

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
            }

        }
            .launchIn(viewModelScope)
    }
}

object VerifyEmail {
    data class UiState(
        val isVerifyingOtp: Boolean = false,
        val isResendingOtp: Boolean = false,
        val verifyOtpError: UiText = UiText.Idle,
        val resendOtpError: UiText = UiText.Idle,
        val data: SignInResponse? = null
    )

    sealed interface Event {
        data class ResendOtp(val email: String) : Event
        data class VerifyEmail(val email: String, val otp: String) : Event

        // navigation event
        data object GoBack : Event
        data object GoToSearchHomeScreen : Event
        data object GoToAccountCreateProfileScreen : Event
    }
}

