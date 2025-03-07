package com.example.common.data_store

import com.example.common.models.Gender
import com.example.common.models.Profile
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