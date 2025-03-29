package com.example.account.domain.use_cases

import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.account.domain.repository.AccountRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    operator fun invoke(newProfile: CreateProfileInput) = flow<NetworkResult<Profile>> {
        emit(NetworkResult.Loading())

        accountRepository.createProfile(newProfile)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message ?: "Unknown Error occurred")) }
    }.flowOn(Dispatchers.IO)
}