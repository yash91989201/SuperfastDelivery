package com.example.auth.domain.session_state_holder

import com.example.auth.domain.model.SessionData
import kotlinx.coroutines.flow.StateFlow

interface SessionStateHolder {
    val session: StateFlow<SessionData?>
    fun updateSession(sessionData: SessionData)
    fun clearSession()
}