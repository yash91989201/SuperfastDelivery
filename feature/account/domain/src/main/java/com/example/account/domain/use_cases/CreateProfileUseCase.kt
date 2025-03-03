package com.example.account.domain.use_cases

import android.util.Log
import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile
import com.example.account.domain.repository.AccountRepository
import com.example.common.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(private val accountRepository: AccountRepository){
    operator fun invoke(newProfile: CreateProfileInput) = flow<NetworkResult<Profile>> {
        emit(NetworkResult.Loading())

        val response = accountRepository.createProfile(newProfile)

        if(response.isSuccess){
            emit(NetworkResult.Success(response.getOrNull()))
        }else{
            emit(NetworkResult.Error(response.exceptionOrNull()?.message))
        }
    }.flowOn(Dispatchers.IO)
}