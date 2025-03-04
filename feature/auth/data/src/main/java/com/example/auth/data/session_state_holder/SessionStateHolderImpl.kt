package com.example.auth.data.session_state_holder

import com.example.auth.data.data_store_manager.SessionDataStoreManager
import com.example.auth.domain.model.SessionData
import com.example.auth.domain.session_state_holder.SessionStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionStateHolderImpl @Inject constructor(private val sessionDataStoreManager: SessionDataStoreManager) :
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
            sessionDataStoreManager.getSession().collect { _session.value = it }
        }
    }

    override fun updateSession(sessionData: SessionData) {
        _session.value = sessionData
        coroutineScope.launch {
            sessionDataStoreManager.saveSession(
                sessionData.accessToken,
                sessionData.accessTokenExpiresAt,
                sessionData.sessionId
            )
        }
    }

    override fun clearSession() {
        _session.value = null
        coroutineScope.launch {
            sessionDataStoreManager.clearSession()
        }
    }
}
