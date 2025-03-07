package com.example.common.state_holder

import com.example.common.models.Auth
import kotlinx.coroutines.flow.StateFlow

interface AuthStateHolder {
    val auth: StateFlow<Auth?>
    fun updateAuth(auth: Auth)
    fun clearAuth()
}