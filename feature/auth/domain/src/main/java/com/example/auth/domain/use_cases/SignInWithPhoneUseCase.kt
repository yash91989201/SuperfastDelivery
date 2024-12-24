package com.example.auth.domain.use_cases

import com.example.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInWithPhoneUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(phone: String, otp: String? = null) = flow<Unit> {
        authRepository.signInWithPhone(phone, otp)
    }.flowOn(Dispatchers.IO)
}