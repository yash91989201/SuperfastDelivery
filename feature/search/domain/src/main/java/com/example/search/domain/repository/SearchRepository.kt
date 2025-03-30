package com.example.search.domain.repository

import com.example.search.domain.model.ListShopsInput
import com.example.search.domain.model.Shop

interface SearchRepository {
    suspend fun listShops(input: ListShopsInput? = null): Result<List<Shop>>
}