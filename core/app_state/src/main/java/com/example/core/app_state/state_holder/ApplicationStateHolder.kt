package com.example.core.app_state.state_holder

interface ApplicationStateHolder {
    val sessionStateHolder: SessionStateHolder
    val authStateHolder: AuthStateHolder
    val profileStateHolder: ProfileStateHolder
}