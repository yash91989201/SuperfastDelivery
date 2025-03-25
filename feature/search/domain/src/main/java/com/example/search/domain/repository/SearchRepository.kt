package com.example.search.domain.repository

import com.example.search.domain.model.ListShopsInput
import com.example.search.domain.model.Shop

interface SearchRepository {
    suspend fun listShops(query: ListShopsInput? = null): Result<List<Shop>>
}