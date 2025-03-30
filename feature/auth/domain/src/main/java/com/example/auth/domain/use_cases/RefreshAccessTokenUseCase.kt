package com.example.auth.domain.use_cases

import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.repository.AuthRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshAccessTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(refreshToken: String) = flow<NetworkResult<SignInResponse>> {
        emit(NetworkResult.Loading())
        authRepository.refreshAccessToken(refreshToken)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.localizedMessage)) }
    }
}