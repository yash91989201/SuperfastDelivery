package com.example.search.data.repository

import com.example.search.data.mappers.toDomain
import com.example.search.data.mappers.toSchema
import com.example.search.data.remote.SearchGraphQLService
import com.example.search.domain.model.ListShopsInput
import com.example.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchGraphQLService: SearchGraphQLService
) : SearchRepository {
    override suspend fun listShops(input: ListShopsInput?) = runCatching {
        val response = searchGraphQLService.listShops(input?.toSchema())

        response.exception?.also { throw Exception(it.toString()) }

        response.errors?.firstOrNull()?.message?.also { throw Exception(it) }

        response.data?.ListShops?.toDomain() ?: throw Exception("No data returned")
    }
}