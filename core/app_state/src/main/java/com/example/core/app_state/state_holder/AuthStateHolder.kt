package com.example.core.app_state.state_holder

import com.example.core.app_state.models.Auth
import kotlinx.coroutines.flow.StateFlow

interface AuthStateHolder {
    val auth: StateFlow<Auth?>
    fun updateAuth(auth: Auth)
    fun clearAuth()
}