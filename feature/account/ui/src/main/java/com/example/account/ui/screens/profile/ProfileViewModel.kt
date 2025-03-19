package com.example.account.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.model.Gender
import com.example.account.domain.model.Profile
import com.example.account.domain.model.UpdateProfileInput
import com.example.account.domain.use_cases.UpdateProfileUseCase
import com.example.common.models.Auth
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.Navigator
import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject
import com.example.common.models.Profile as StoreProfile

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigator: Navigator,
    applicationStateHolder: ApplicationStateHolder,
    private val updateProfileUseCase: UpdateProfileUseCase,
) : ViewModel() {

    private val _auth = applicationStateHolder.authStateHolder.auth
    val auth: StateFlow<Auth?> = _auth

    private val _profile = applicationStateHolder.profileStateHolder.profile
    val profile: StateFlow<StoreProfile?> = _profile

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

    private val _uiState = MutableStateFlow(ProfileScreenState.UIState())
    val uiState: StateFlow<ProfileScreenState.UIState> = _uiState.asStateFlow()


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

    fun onEvent(event: ProfileScreenState.Event) {
        when (event) {
            is ProfileScreenState.Event.UpdateProfile -> {
                updateProfile(event.updatedProfile)
            }

            ProfileScreenState.Event.GoToSearchHomeScreen -> {
                navigator.navigateTo(NavigationSubGraphDest.SearchHome)
            }

            ProfileScreenState.Event.GoBack -> {
                navigator.navigateBack()
            }
        }
    }

    private fun updateProfile(newProfile: UpdateProfileInput) {
        updateProfileUseCase(newProfile).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is NetworkResult.Success -> {
                    onEvent(ProfileScreenState.Event.GoToSearchHomeScreen)
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

object ProfileScreenState {
    data class UIState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: Profile? = null
    )

    sealed interface Event {
        data object GoBack : Event
        data object GoToSearchHomeScreen : Event

        data class UpdateProfile(val updatedProfile: UpdateProfileInput) : Event
    }
}