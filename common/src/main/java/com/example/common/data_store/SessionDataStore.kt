package com.example.common.data_store

import com.example.common.models.Session
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