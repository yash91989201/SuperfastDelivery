package com.example.search.domain.use_cases

import com.example.common.utils.NetworkResult
import com.example.search.domain.model.ListShopsInput
import com.example.search.domain.model.Shop
import com.example.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListShopsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(input: ListShopsInput? = null) = flow<NetworkResult<List<Shop>>> {
        emit(NetworkResult.Loading())

        searchRepository.listShops(input).fold(
            onSuccess = {
                emit(NetworkResult.Success(it))
            },
            onFailure = {
                emit(NetworkResult.Error(it.message))
            }
        )
    }
}