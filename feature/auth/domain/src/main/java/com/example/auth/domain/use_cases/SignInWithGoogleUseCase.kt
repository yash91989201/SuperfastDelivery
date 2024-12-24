package com.example.auth.domain.use_cases

import com.example.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(idToken:String) = flow<Unit> {
        authRepository.signInWithGoogle(idToken)
    }.flowOn(Dispatchers.IO)
}