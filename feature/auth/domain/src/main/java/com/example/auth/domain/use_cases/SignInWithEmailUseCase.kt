package com.example.auth.domain.use_cases

import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.repository.AuthRepository
import com.example.common.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, otp: String? = null) = flow<NetworkResult<SignInResponse>> {
        emit(NetworkResult.Loading())
        val response = authRepository.signInWithEmail(email, otp)
        if (response.isSuccess) {
            emit(NetworkResult.Success(response.getOrNull()))
        } else {
            emit(NetworkResult.Error(response.exceptionOrNull()?.message))
        }
    }.flowOn(Dispatchers.IO)
}