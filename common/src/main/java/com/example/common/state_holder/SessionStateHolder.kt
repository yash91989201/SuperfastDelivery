package com.example.common.state_holder

import com.example.common.models.Session
import kotlinx.coroutines.flow.StateFlow

interface SessionStateHolder {
    val session: StateFlow<Session?>
    fun updateSession(session: Session)
    fun clearSession()
}