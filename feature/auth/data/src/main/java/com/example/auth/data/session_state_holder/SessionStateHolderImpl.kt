package com.example.auth.data.session_state_holder

import com.example.common.application_state_store.SessionDataStore
import com.example.common.application_state_store.SessionStateHolder
import com.example.common.models.SessionData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionStateHolderImpl @Inject constructor(private val sessionDataStore: SessionDataStore) :
    SessionStateHolder {
    private val _session = MutableStateFlow<SessionData?>(null)
    override val session: StateFlow<SessionData?>
        get() = _session.asStateFlow()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        loadSession()
    }

    private fun loadSession() {
        coroutineScope.launch {
            sessionDataStore.getSession().collect { _session.value = it }
        }
    }

    override fun updateSession(sessionData: SessionData) {
        _session.value = sessionData
        coroutineScope.launch {
            sessionDataStore.saveSession(
                sessionData.authId,
                sessionData.accessToken,
                sessionData.accessTokenExpiresAt,
                sessionData.sessionId
            )
        }
    }

    override fun clearSession() {
        _session.value = null
        coroutineScope.launch {
            sessionDataStore.clearSession()
        }
    }
}
