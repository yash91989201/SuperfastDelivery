package com.example.account.domain.use_cases

import com.example.account.domain.model.DefaultDeliveryAddress
import com.example.account.domain.repository.AccountRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDefaultDeliveryAddressUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke() = flow<NetworkResult<DefaultDeliveryAddress>> {
        emit(NetworkResult.Loading())

        accountRepository.getDefaultDeliveryAddress()
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message ?: "Unknown Error occurred")) }
    }.flowOn(Dispatchers.IO)
}