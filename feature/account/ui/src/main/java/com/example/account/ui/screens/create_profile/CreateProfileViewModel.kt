package com.example.account.ui.screens.create_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Gender
import com.example.account.domain.model.Profile
import com.example.account.domain.use_cases.CreateProfileUseCase
import com.example.core.app_state.models.Auth
import com.example.core.app_state.state_holder.ApplicationStateHolder
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
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val navigator: Navigator,
    applicationStateHolder: ApplicationStateHolder,
    private val createProfileUseCase: CreateProfileUseCase,
) : ViewModel() {

    private val _auth = applicationStateHolder.authStateHolder.auth
    val auth: StateFlow<Auth?> = _auth

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _gender = MutableStateFlow<Gender?>(null)
    val gender: StateFlow<Gender?> = _gender.asStateFlow()

    private val _dob = MutableStateFlow<LocalDate?>(null)
    val dob: StateFlow<LocalDate?> = _dob.asStateFlow()

    private val _anniversary = MutableStateFlow<LocalDate?>(null)
    val anniversary: StateFlow<LocalDate?> = _anniversary.asStateFlow()

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl.asStateFlow()

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    private val _uiState = MutableStateFlow(CreateProfile.UIState())
    val uiState: StateFlow<CreateProfile.UIState> = _uiState.asStateFlow()


    fun updateName(value: String) {
        _name.update { value }
    }

    fun updateGender(value: Gender?) {
        _gender.value = value
    }

    fun updateDob(value: LocalDate?) {
        _dob.update { value }
    }

    fun updateAnniversary(value: LocalDate?) {
        _anniversary.update { value }
    }

    fun updateImageUrl(value: String?) {
        _imageUrl.update { value }
    }

    fun onEvent(event: CreateProfile.Event) {
        when (event) {
            is CreateProfile.Event.CreateProfile -> {
                if (validateForm()) {
                    createProfile(event.newProfile)
                }
            }

            CreateProfile.Event.GoToSearchHomeScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.SearchHome)
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (name.value.length < 6) {
            _nameError.update { "Provide your full name." }
            isValid = false
        } else {
            _nameError.update { null }
        }

        return isValid
    }

    private fun createProfile(newProfile: CreateProfileInput) {
        createProfileUseCase(newProfile).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    onEvent(CreateProfile.Event.GoToSearchHomeScreen)
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

object CreateProfile {
    data class UIState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: Profile? = null
    )

    sealed interface Event {
        data object GoToSearchHomeScreen : Event

        data class CreateProfile(val newProfile: CreateProfileInput) : Event
    }
}