package com.example.core.network

interface TokenRefresher {
    suspend fun refreshAccessToken():Boolean
}