package com.example.common.application_state_store

import com.example.common.models.SessionData
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface SessionDataStore {
    suspend fun saveSession(
        authId: String,
        accessToken: String,
        accessTokenExpiresAt: Instant,
        sessionId: String
    )

    fun getSession(): Flow<SessionData?>
    suspend fun clearSession()
}