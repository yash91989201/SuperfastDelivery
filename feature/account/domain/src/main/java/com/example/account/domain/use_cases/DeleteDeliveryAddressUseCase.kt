package com.example.account.domain.use_cases

import com.example.account.domain.repository.AccountRepository
import com.example.common.utils.NetworkResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteDeliveryAddressUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(addressId: String) = flow<NetworkResult<String>> {
        emit(NetworkResult.Loading())

        accountRepository.deleteDeliveryAddress(addressId)
            .onSuccess { emit(NetworkResult.Success(it)) }
            .onFailure { emit(NetworkResult.Error(it.message ?: "Unknown Error occurred")) }
    }

}