package com.example.account.domain.use_cases

import com.example.account.domain.model.Profile
import com.example.account.domain.model.UpdateProfileInput
import com.example.account.domain.repository.AccountRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    operator fun invoke(updatedProfile: UpdateProfileInput) = flow<NetworkResult<Profile>> {
        emit(NetworkResult.Loading())

        accountRepository.updateProfile(updatedProfile)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message ?: "Unknown Error occurred")) }
    }.flowOn(Dispatchers.IO)
}