package com.example.account.ui.screens.create_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.account.domain.use_cases.CreateProfileUseCase
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
class CreateProfileViewModel @Inject constructor(
    private val createProfileUseCase: CreateProfileUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProfile.UIState())
    val uiState: StateFlow<CreateProfile.UIState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<CreateProfile.Navigation>()
    val navigation: Flow<CreateProfile.Navigation> get() = _navigation.receiveAsFlow()

    fun onEvent(event: CreateProfile.Event) {
        when (event) {
            is CreateProfile.Event.CreateProfile -> {
                createProfile(event.newProfile)
            }

            CreateProfile.Event.GoToSearchScreen -> {
                viewModelScope.launch {
                    _navigation.send(CreateProfile.Navigation.GoToSearchScreen)
                }
            }
        }
    }

    private fun createProfile(newProfile: CreateProfileInput) {
        createProfileUseCase.invoke(newProfile).onEach { result ->
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
                    _navigation.send(CreateProfile.Navigation.GoToSearchScreen)
                }
            }
        }.launchIn(viewModelScope)
    }
}

object CreateProfile {
    data class UIState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: Profile? = null
    )

    sealed interface Navigation {
        data object GoToSearchScreen : Navigation
    }

    sealed interface Event {
        data class CreateProfile(val newProfile: CreateProfileInput) : Event

        // navigation events
        data object GoToSearchScreen : Event
    }
}