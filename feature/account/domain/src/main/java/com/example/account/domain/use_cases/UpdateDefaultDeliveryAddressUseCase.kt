package com.example.account.domain.use_cases

import com.example.account.domain.repository.AccountRepository
import com.example.common.utils.NetworkResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateDefaultDeliveryAddressUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(deliveryAddressId: String, authId: String) = flow<NetworkResult<String>> {
        emit(NetworkResult.Loading())

        accountRepository.updateDefaultDeliveryAddress(deliveryAddressId, authId)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message ?: "Unknown Error occurred")) }
    }
}