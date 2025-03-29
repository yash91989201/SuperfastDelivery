package com.example.core.app_state.data_store

import com.example.core.app_state.models.Session
import kotlinx.coroutines.flow.Flow

interface SessionDataStore {
    suspend fun saveSession(
        refreshToken: String,
        accessToken: String,
    )

    fun getSession(): Flow<Session?>
    suspend fun clearSession()
}