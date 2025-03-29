package com.example.core.app_state.data_store

import com.example.core.app_state.models.Auth
import com.example.core.app_state.models.AuthRole
import kotlinx.coroutines.flow.Flow

interface AuthDataStore {
    suspend fun saveAuth(
        id: String,
        email: String?,
        emailVerified: Boolean,
        phone: String?,
        authRole: AuthRole
    )

    fun getAuth(): Flow<Auth?>
    suspend fun clearAuth()
}