package com.example.account.domain.use_cases

import com.example.account.domain.repository.AccountRepository
import com.example.core.utils.NetworkResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateDefaultDeliveryAddressUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(deliveryAddressId: String) = flow<NetworkResult<String>> {
        emit(NetworkResult.Loading())

        accountRepository.updateDefaultDeliveryAddress(deliveryAddressId)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message ?: "Unknown Error occurred")) }
    }
}