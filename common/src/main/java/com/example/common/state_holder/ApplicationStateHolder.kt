package com.example.common.state_holder

interface ApplicationStateHolder {
    val sessionStateHolder: SessionStateHolder
    val authStateHolder: AuthStateHolder
}