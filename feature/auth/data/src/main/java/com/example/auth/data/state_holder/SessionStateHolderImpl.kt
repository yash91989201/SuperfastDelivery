package com.example.auth.data.state_holder

import com.example.core.app_state.data_store.SessionDataStore
import com.example.core.app_state.models.Session
import com.example.core.app_state.state_holder.SessionStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SessionStateHolderImpl @Inject constructor(private val sessionDataStore: SessionDataStore) :
    SessionStateHolder {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _session = sessionDataStore
        .getSession()
        .stateIn(
            coroutineScope,
            SharingStarted.Eagerly,
            null
        )

    private val _isSessionLoaded = _session
        .map { true }
        .stateIn(
            coroutineScope,
            SharingStarted.Eagerly,
            false
        )

    override val session: StateFlow<Session?>
        get() = _session

    override val isSessionLoaded: StateFlow<Boolean>
        get() = _isSessionLoaded

    override suspend fun updateSession(session: Session) {
        sessionDataStore.saveSession(
            accessToken = session.accessToken,
            refreshToken = session.refreshToken
        )
    }

    override suspend fun clearSession() {
        sessionDataStore.clearSession()
    }
}