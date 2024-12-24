package com.example.auth.ui.screens.email_sign_in

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
class EmailSignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailSignIn.UiState())
    val uiState: StateFlow<EmailSignIn.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<EmailSignIn.Navigation>()
    val navigation: Flow<EmailSignIn.Navigation> get() = _navigation.receiveAsFlow()

    fun onEvent(event: EmailSignIn.Event) {
        when (event) {
            EmailSignIn.Event.GoToVerifyEmailScreen -> {
                viewModelScope.launch {
                    _navigation.send(EmailSignIn.Navigation.GoToVerifyEmailScreen)
                }
            }

            is EmailSignIn.Event.SignInWithEmail -> {
                signInWithEmail(event.email)
            }
        }
    }

    private fun signInWithEmail(email: String) {
        signInWithEmailUseCase.invoke(email).onEach { result ->
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
                        if (it.verityOtp) {
                            _navigation.send(EmailSignIn.Navigation.GoToVerifyEmailScreen)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

object EmailSignIn {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: SignInResponse? = null
    )

    sealed interface Navigation {
        data object GoToVerifyEmailScreen : Navigation
    }

    sealed interface Event {
        data class SignInWithEmail(val email: String) : Event

        // navigation events
        data object GoToVerifyEmailScreen : Event
    }
}