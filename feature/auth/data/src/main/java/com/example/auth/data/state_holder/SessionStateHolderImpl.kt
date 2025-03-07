package com.example.auth.data.state_holder

import com.example.common.data_store.SessionDataStore
import com.example.common.models.Session
import com.example.common.state_holder.SessionStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionStateHolderImpl @Inject constructor(private val sessionDataStore: SessionDataStore) :
    SessionStateHolder {
    private val _session = MutableStateFlow<Session?>(null)
    override val session: StateFlow<Session?>
        get() = _session.asStateFlow()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        loadSession()
    }

    private fun loadSession() {
        coroutineScope.launch {
            sessionDataStore.getSession().collect { _session.update { it } }
        }
    }

    override fun updateSession(session: Session) {
        _session.update { session }
        coroutineScope.launch {
            sessionDataStore.saveSession(
                sessionId = session.sessionId,
                accessToken = session.accessToken,
                accessTokenExpiresAt = session.accessTokenExpiresAt,
            )
        }
    }

    override fun clearSession() {
        _session.update { null }
        coroutineScope.launch {
            sessionDataStore.clearSession()
        }
    }
}