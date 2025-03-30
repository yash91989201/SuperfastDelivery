package com.example.auth.ui.screens.email_sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.model.AuthRole
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
class EmailSignInViewModel @Inject constructor(
    private val navigator: Navigator,
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailSignIn.UiState())
    val uiState: StateFlow<EmailSignIn.UiState> get() = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email.asStateFlow()

    fun onEvent(event: EmailSignIn.Event) {
        when (event) {
            EmailSignIn.Event.GoToVerifyEmailScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AuthVerifyEmail(_email.value))
            }

            is EmailSignIn.Event.SignInWithEmail -> {
                signInWithEmail(event.email)
            }

            is EmailSignIn.Event.UpdateEmail -> {
                _email.update { event.email }
            }

            EmailSignIn.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

    private fun signInWithEmail(email: String) {
        signInWithEmailUseCase(email, AuthRole.CUSTOMER).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }

                    result.data?.let {
                        if (it.verityOtp) {
                            onEvent(EmailSignIn.Event.GoToVerifyEmailScreen)
                        }
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = UiText.RemoteString(result.message ?: "Unknown Error")
                        )
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

    sealed interface Event {
        data class SignInWithEmail(val email: String) : Event
        data class UpdateEmail(val email: String) : Event

        // navigation events
        data object GoBack : Event
        data object GoToVerifyEmailScreen : Event
    }
}