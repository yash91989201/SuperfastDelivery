package com.example.auth.data.state_holder

import com.example.core.app_state.data_store.AuthDataStore
import com.example.core.app_state.models.Auth
import com.example.core.app_state.state_holder.AuthStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthStateHolderImpl @Inject constructor(
    private val authDataStore: AuthDataStore
) : AuthStateHolder {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _auth = authDataStore
        .getAuth()
        .stateIn(
            coroutineScope,
            SharingStarted.Lazily,
            null
        )

    override val auth: StateFlow<Auth?>
        get() = _auth

    override suspend fun updateAuth(auth: Auth) {
        authDataStore.saveAuth(
            id = auth.id,
            email = auth.email,
            emailVerified = auth.emailVerified,
            phone = auth.phone,
            authRole = auth.authRole
        )
    }

    override suspend fun clearAuth() {
        authDataStore.clearAuth()
    }
}