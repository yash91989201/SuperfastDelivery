package com.example.auth.ui.screens.home

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
class HomeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val signInWithPhoneUseCase: SignInWithPhoneUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeModel.UiState())
    val uiState: StateFlow<HomeModel.UiState> get() = _uiState.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> get() = _phone.asStateFlow()

    fun onEvent(event: HomeModel.Event) {
        when (event) {
            HomeModel.Event.GoToVerifyPhoneScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AuthVerifyPhone(phone = _phone.value))
            }

            is HomeModel.Event.SignInWithPhone -> {
                signInWithPhone(event.phone)
            }

            is HomeModel.Event.UpdatePhone -> {
                _phone.update { event.phone.trim() }
            }

            HomeModel.Event.GoToEmailSignInScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.AuthEmailSignIn)
            }
        }
    }

    private fun signInWithPhone(phone: String) {
        signInWithPhoneUseCase(phone).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }

                    result.data?.let {
                        if (it.verifyOtp) {
                            onEvent(HomeModel.Event.GoToVerifyPhoneScreen)
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

object HomeModel {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: SignInResponse? = null
    )

    sealed interface Event {
        data class SignInWithPhone(val phone: String) : Event
        data class UpdatePhone(val phone: String) : Event

        // navigation events
        data object GoToEmailSignInScreen : Event
        data object GoToVerifyPhoneScreen : Event
    }
}