package com.example.common.state_holder

import com.example.common.models.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileStateHolder {
    val profile: StateFlow<Profile?>
    fun updateProfile(profile: Profile)
    fun clearProfile()
}