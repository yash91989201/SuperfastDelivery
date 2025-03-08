package com.example.auth.data.state_holder

import com.example.common.data_store.AuthDataStore
import com.example.common.models.Auth
import com.example.common.state_holder.AuthStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthStateHolderImpl @Inject constructor(
    private val authDataStore: AuthDataStore
) : AuthStateHolder {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _auth = authDataStore.getAuth().stateIn(coroutineScope, SharingStarted.Lazily, null)
    override val auth: StateFlow<Auth?>
        get() = _auth


    override fun updateAuth(auth: Auth) {
        coroutineScope.launch {
            authDataStore.saveAuth(
                id = auth.id,
                email = auth.email,
                emailVerified = auth.emailVerified,
                phone = auth.phone,
                authRole = auth.authRole
            )
        }
    }

    override fun clearAuth() {
        coroutineScope.launch {
            authDataStore.clearAuth()
        }
    }
}