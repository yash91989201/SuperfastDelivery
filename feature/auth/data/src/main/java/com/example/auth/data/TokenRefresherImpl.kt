package com.example.auth.data

import com.example.auth.domain.repository.AuthRepository
import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.network.TokenRefresher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Provider

class TokenRefresherImpl(
    private val authRepository: Provider<AuthRepository>,
    private val applicationStateHolder: ApplicationStateHolder
) : TokenRefresher {
    private val tokenRefreshLock = Mutex()
    private var lastRefreshTime = 0L
    private val minimumRefreshInterval = 15 * 60 * 1000L

    override suspend fun refreshAccessToken(): Boolean = tokenRefreshLock.withLock {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastRefresh = currentTime - lastRefreshTime

        if (timeSinceLastRefresh < minimumRefreshInterval) {
            return true
        }

        try {
            val authRepo = authRepository.get() ?: return false
            val session = applicationStateHolder.sessionStateHolder.session.value ?: return false

            val refreshResult = authRepo.refreshAccessToken(session.refreshToken)
            if (refreshResult.isSuccess) {
                lastRefreshTime = System.currentTimeMillis()
            }
            return refreshResult.isSuccess
        } catch (e: Exception) {
            return false
        }
    }
}