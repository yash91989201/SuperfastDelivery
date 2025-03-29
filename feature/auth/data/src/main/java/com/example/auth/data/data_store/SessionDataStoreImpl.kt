package com.example.auth.data.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.app_state.data_store.SessionDataStore
import com.example.core.app_state.models.Session
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

private const val SESSION_DATASTORE = "session"

private val Context.sessionDataStore by preferencesDataStore(name = SESSION_DATASTORE)

class SessionDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionDataStore {

    private val dataStore = context.sessionDataStore

    companion object {
        private val SESSION_ID = stringPreferencesKey("session_id")
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val ACCESS_TOKEN_EXPIRES_AT = longPreferencesKey("access_token_expires_at")
    }

    override suspend fun saveSession(
        sessionId: String,
        accessToken: String,
        accessTokenExpiresAt: Instant,
    ) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = accessToken
            prefs[ACCESS_TOKEN_EXPIRES_AT] = accessTokenExpiresAt.epochSecond
            prefs[SESSION_ID] = sessionId
        }
    }

    override fun getSession(): Flow<Session?> = dataStore.data.map { prefs ->
        val accessToken = prefs[ACCESS_TOKEN]
        val accessTokenExpiresAt = prefs[ACCESS_TOKEN_EXPIRES_AT]
        val sessionId = prefs[SESSION_ID]

        if (accessToken != null && accessTokenExpiresAt != null && sessionId != null)
            Session(
                accessToken = accessToken,
                accessTokenExpiresAt = Instant.ofEpochSecond(accessTokenExpiresAt),
                id = sessionId
            )
        else null
    }

    override suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }
}
