package com.example.auth.domain.use_cases

import com.example.auth.domain.model.AuthRole
import com.example.auth.domain.model.SignInResponse
import com.example.auth.domain.repository.AuthRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(idToken: String) =
        flow<NetworkResult<SignInResponse>> {
            emit(NetworkResult.Loading())

            authRepository.signInWithGoogle(
                idToken = idToken,
                authRole = AuthRole.CUSTOMER
            )
                .onSuccess { emit(NetworkResult.Success(it)) }
                .onFailure { emit(NetworkResult.Error(it.message)) }
        }.flowOn(Dispatchers.IO)
}