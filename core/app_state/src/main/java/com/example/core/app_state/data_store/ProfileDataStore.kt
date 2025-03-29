package com.example.core.app_state.data_store

import com.example.core.app_state.models.Gender
import com.example.core.app_state.models.Profile
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ProfileDataStore {
    suspend fun saveProfile(
        id: String,
        name: String,
        imageUrl: String?,
        dob: LocalDate?,
        anniversary: LocalDate?,
        gender: Gender?,
        authId: String,
    )

    fun getProfile(): Flow<Profile?>
    suspend fun clearProfile()
}