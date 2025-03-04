package com.example.auth.data.data_store_manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.auth.domain.model.SessionData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

const val SESSION_DATASTORE = "session"

val Context.sessionDataStore by preferencesDataStore(name = SESSION_DATASTORE)

class SessionDataStoreManager(@ApplicationContext context: Context) {
    private val dataStore = context.sessionDataStore

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val ACCESS_TOKEN_EXPIRES_AT = longPreferencesKey("access_token_expires_at")
        private val SESSION_ID = stringPreferencesKey("session_id")
    }

    suspend fun saveSession(
        accessToken: String,
        accessTokenExpiresAt: Instant,
        sessionId: String
    ) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = accessToken
            prefs[ACCESS_TOKEN_EXPIRES_AT] = accessTokenExpiresAt.epochSecond
            prefs[SESSION_ID] = sessionId
        }
    }

    fun getSession(): Flow<SessionData?> = dataStore.data.map { prefs ->
        val accessToken = prefs[ACCESS_TOKEN]
        val accessTokenExpiresAt = prefs[ACCESS_TOKEN_EXPIRES_AT]
        val sessionId = prefs[SESSION_ID]

        if (accessToken != null && accessTokenExpiresAt != null && sessionId != null)
            SessionData(
                accessToken = accessToken,
                accessTokenExpiresAt = Instant.ofEpochSecond(accessTokenExpiresAt),
                sessionId = sessionId
            )
        else null
    }

    suspend fun clearSession() = dataStore.edit { it.clear() }
}