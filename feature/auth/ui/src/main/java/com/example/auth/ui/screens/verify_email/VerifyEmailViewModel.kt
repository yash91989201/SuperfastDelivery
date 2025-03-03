package com.example.auth.ui.screens.verify_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.use_cases.SignInWithEmailUseCase
import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyEmail.UiState())
    val uiState: StateFlow<VerifyEmail.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<VerifyEmail.Navigation>()
    val navigation: Flow<VerifyEmail.Navigation> get() = _navigation.receiveAsFlow()

    fun onEvent(event: VerifyEmail.Event) {
        when (event) {
            VerifyEmail.Event.GoToSearchHomeScreen -> {
                viewModelScope.launch {
                    _navigation.send(VerifyEmail.Navigation.GoToSearchHomeScreen)
                }
            }

            is VerifyEmail.Event.GoToAccountCreateProfileScreen -> {
                viewModelScope.launch {
                    _navigation.send(VerifyEmail.Navigation.GoToAccountCreateProfileScreen)
                }
            }

            is VerifyEmail.Event.VerifyEmail -> {
                verifyEmail(email = event.email, otp = event.otp)
            }

            is VerifyEmail.Event.ResendEmail -> {
                resendEmail(email = event.email)
            }
        }
    }

    private fun verifyEmail(email: String, otp: String) {
        signInWithEmailUseCase.invoke(email = email, otp = otp).onEach { result ->
            when (result) {
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isVerifyingOtp = false,
                            verifyOtpError = UiText.RemoteString(result.message ?: "Unknown Error")
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _uiState.update {
                        it.copy(
                            isVerifyingOtp = true
                        )
                    }
                }

                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isVerifyingOtp = false
                        )
                    }

                    result.data?.let {
                        if (it.createProfile) {
                            _navigation.send(
                                VerifyEmail.Navigation.GoToAccountCreateProfileScreen
                            )
                        }else{
                            _navigation.send(
                                VerifyEmail.Navigation.GoToSearchHomeScreen
                            )
                        }
                    }
                }
            }
        }
            .launchIn(viewModelScope)
    }

    private fun resendEmail(email: String) {
        signInWithEmailUseCase.invoke(email = email).onEach { result ->
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

    sealed interface Navigation {
        data object GoToSearchHomeScreen : Navigation
        data object GoToAccountCreateProfileScreen : Navigation
    }

    sealed interface Event {
        data class ResendEmail(val email: String) : Event
        data class VerifyEmail(val email: String, val otp: String) : Event

        // navigation event
        data object GoToSearchHomeScreen : Event
        data object GoToAccountCreateProfileScreen : Event
    }
}

