package com.example.common.data_store

import com.example.common.models.Auth
import com.example.common.models.AuthRole
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