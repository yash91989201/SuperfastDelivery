package com.example.core.app_state.data_store

import com.example.core.app_state.models.Session
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface SessionDataStore {
    suspend fun saveSession(
        sessionId: String,
        accessToken: String,
        accessTokenExpiresAt: Instant,
    )

    fun getSession(): Flow<Session?>
    suspend fun clearSession()
}