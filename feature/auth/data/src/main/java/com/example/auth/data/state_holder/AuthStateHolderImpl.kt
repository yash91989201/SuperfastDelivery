package com.example.auth.data.state_holder

import com.example.common.data_store.AuthDataStore
import com.example.common.models.Auth
import com.example.common.state_holder.AuthStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthStateHolderImpl @Inject constructor(
    private val authDataStore: AuthDataStore
) : AuthStateHolder {
    private val _auth = MutableStateFlow<Auth?>(null)
    override val auth: StateFlow<Auth?>
        get() = _auth.asStateFlow()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        loadAuth()
    }

    private fun loadAuth() {
        coroutineScope.launch {
            authDataStore.getAuth().collect { _auth.update { it } }
        }
    }

    override fun updateAuth(authData: Auth) {
        _auth.update { authData }
        coroutineScope.launch {
            authDataStore.saveAuth(
                id = authData.id,
                email = authData.email,
                emailVerified = authData.emailVerified,
                phone = authData.phone,
                authRole = authData.authRole
            )
        }
    }

    override fun clearAuth() {
        _auth.update { null }
        coroutineScope.launch {
            authDataStore.clearAuth()
        }
    }
}