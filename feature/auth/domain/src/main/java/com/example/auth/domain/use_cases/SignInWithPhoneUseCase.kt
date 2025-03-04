package com.example.auth.domain.use_cases

import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.repository.AuthRepository
import com.example.common.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInWithPhoneUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(phone: String, otp: String? = null) = flow<NetworkResult<SignInResponse>> {
        emit(NetworkResult.Loading())

        authRepository.signInWithPhone(phone, otp)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message)) }
    }.flowOn(Dispatchers.IO)
}