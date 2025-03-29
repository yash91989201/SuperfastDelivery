package com.example.account.data.state_holder

import com.example.core.app_state.data_store.ProfileDataStore
import com.example.core.app_state.models.Profile
import com.example.core.app_state.state_holder.ProfileStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileStateHolderImpl @Inject constructor(
    private val profileDataStore: ProfileDataStore,
) : ProfileStateHolder {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _profile =
        profileDataStore.getProfile().stateIn(coroutineScope, SharingStarted.Lazily, null)
    override val profile: StateFlow<Profile?>
        get() = _profile

    override fun updateProfile(profile: Profile) {
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
        coroutineScope.launch {
            profileDataStore.clearProfile()
        }
    }
}