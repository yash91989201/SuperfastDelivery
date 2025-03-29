package com.example.auth.domain.use_cases

import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.repository.AuthRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(sessionId: String) = flow<NetworkResult<SignInResponse>> {
        emit(NetworkResult.Loading())
        authRepository.refreshToken(sessionId)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.localizedMessage)) }
    }
}