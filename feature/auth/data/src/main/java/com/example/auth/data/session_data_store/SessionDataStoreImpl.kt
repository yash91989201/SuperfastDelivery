package com.example.auth.data.session_data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.common.application_state_store.SessionDataStore
import com.example.common.models.SessionData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

const val SESSION_DATASTORE = "session"

val Context.sessionDataStore by preferencesDataStore(name = SESSION_DATASTORE)

class SessionDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionDataStore {

    private val dataStore = context.sessionDataStore

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val ACCESS_TOKEN_EXPIRES_AT = longPreferencesKey("access_token_expires_at")
        private val SESSION_ID = stringPreferencesKey("session_id")
    }

    override suspend fun saveSession(
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

    override fun getSession(): Flow<SessionData?> = dataStore.data.map { prefs ->
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

    override suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }
}
