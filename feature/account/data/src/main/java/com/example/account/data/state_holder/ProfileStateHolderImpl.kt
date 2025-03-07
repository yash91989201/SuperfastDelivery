package com.example.account.data.state_holder

import com.example.common.data_store.ProfileDataStore
import com.example.common.models.Profile
import com.example.common.state_holder.ProfileStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileStateHolderImpl @Inject constructor(
    private val profileDataStore: ProfileDataStore,
) : ProfileStateHolder {

    private val _profile = MutableStateFlow<Profile?>(null)
    override val profile: StateFlow<Profile?>
        get() = _profile.asStateFlow()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        loadProfile()
    }

    private fun loadProfile() {
        coroutineScope.launch {
            profileDataStore.getProfile().collect { _profile.update { it } }
        }
    }

    override fun updateProfile(profile: Profile) {
        _profile.update { profile }

        coroutineScope.launch {
            profileDataStore.saveProfile(
                id = profile.id,
                name = profile.name,
                imageUrl = profile.imageUrl,
                dob = profile.dob,
                anniversary = profile.anniversary,
                gender = profile.gender,
                authId = profile.authId
            )
        }
    }

    override fun clearProfile() {
        _profile.update { null }
        coroutineScope.launch {
            profileDataStore.clearProfile()
        }
    }
}