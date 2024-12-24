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
            VerifyEmail.Event.GoToHomeScreen -> {
                viewModelScope.launch {
                    _navigation.send(VerifyEmail.Navigation.GoToHomeScreen)
                }
            }

            VerifyEmail.Event.GoToProfileScreen -> {
                viewModelScope.launch {
                    _navigation.send(VerifyEmail.Navigation.GoToProfileScreen)
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
                            isLoading = false,
                            error = UiText.RemoteString(result.message ?: "Unknown Error")
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is NetworkResult.Success -> {
                    result.data?.let {
                        if (it.profile == null) {
                            _navigation.send(VerifyEmail.Navigation.GoToHomeScreen)
                        }
                    }
                }
            }

        }
            .launchIn(viewModelScope)
    }

    private fun resendEmail(email: String) {

    }
}

object VerifyEmail {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: SignInResponse? = null
    )

    sealed interface Navigation {
        data object GoToHomeScreen : Navigation
        data object GoToProfileScreen : Navigation
    }

    sealed interface Event {
        data class ResendEmail(val email: String) : Event
        data class VerifyEmail(val email: String, val otp: String) : Event

        // navigation event
        data object GoToHomeScreen : Event
        data object GoToProfileScreen : Event
    }
}

