package com.example.auth.data.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.app_state.data_store.SessionDataStore
import com.example.core.app_state.models.Session
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val SESSION_DATASTORE = "session"

private val Context.sessionDataStore by preferencesDataStore(name = SESSION_DATASTORE)

class SessionDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionDataStore {

    private val dataStore = context.sessionDataStore

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override suspend fun saveSession(
        refreshToken: String,
        accessToken: String,
    ) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = accessToken
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

    override fun getSession(): Flow<Session?> = dataStore.data.map { prefs ->
        val accessToken = prefs[ACCESS_TOKEN]
        val refreshToken = prefs[REFRESH_TOKEN]

        if (accessToken != null && refreshToken != null)
            Session(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        else null
    }

    override suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }
}