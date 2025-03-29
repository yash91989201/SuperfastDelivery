package com.example.account.domain.use_cases

import com.example.account.domain.model.ListDeliveryAddress
import com.example.account.domain.repository.AccountRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ListDeliveryAddressesUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    operator fun invoke(authId: String) = flow<NetworkResult<ListDeliveryAddress>> {
        emit(NetworkResult.Loading())

        accountRepository.listDeliveryAddresses(authId = authId)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message ?: "Unknown Error occurred")) }
    }.flowOn(Dispatchers.IO)
}