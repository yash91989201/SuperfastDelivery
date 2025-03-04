package com.example.common.application_state_store

import com.example.common.models.SessionData
import kotlinx.coroutines.flow.StateFlow

interface SessionStateHolder {
    val session: StateFlow<SessionData?>
    fun updateSession(sessionData: SessionData)
    fun clearSession()
}