package com.example.auth.data.state_holder

import com.example.core.app_state.data_store.SessionDataStore
import com.example.core.app_state.models.Session
import com.example.core.app_state.state_holder.SessionStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionStateHolderImpl @Inject constructor(private val sessionDataStore: SessionDataStore) :
    SessionStateHolder {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _session = sessionDataStore.getSession()
        .stateIn(coroutineScope, SharingStarted.Lazily, null)
    override val session: StateFlow<Session?>
        get() = _session

    override fun updateSession(session: Session) {
        coroutineScope.launch {
            sessionDataStore.saveSession(
                accessToken = session.accessToken,
                refreshToken = session.refreshToken
            )
        }
    }

    override fun clearSession() {
        coroutineScope.launch {
            sessionDataStore.clearSession()
        }
    }
}