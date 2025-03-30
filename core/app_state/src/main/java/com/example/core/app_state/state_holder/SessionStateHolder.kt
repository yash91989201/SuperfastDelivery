package com.example.core.app_state.state_holder

import com.example.core.app_state.models.Session
import kotlinx.coroutines.flow.StateFlow

interface SessionStateHolder {
    val session: StateFlow<Session?>
    val isSessionLoaded: StateFlow<Boolean>
    suspend fun updateSession(session: Session)
    suspend fun clearSession()
}