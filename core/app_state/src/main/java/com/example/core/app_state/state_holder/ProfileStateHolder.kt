package com.example.core.app_state.state_holder

import com.example.core.app_state.models.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileStateHolder {
    val profile: StateFlow<Profile?>
    fun updateProfile(profile: Profile)
    fun clearProfile()
}