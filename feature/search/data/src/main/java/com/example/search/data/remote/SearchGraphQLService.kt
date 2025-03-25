package com.example.search.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.example.schema.ListShopsQuery
import com.example.schema.type.ListShopsInput

interface SearchGraphQLService {
    suspend fun listShops(input: ListShopsInput? = null): ApolloResponse<ListShopsQuery.Data>
}

class SearchGraphQLServiceImpl(
    private val apolloClient: ApolloClient
) : SearchGraphQLService {
    override suspend fun listShops(input: ListShopsInput?) =
        apolloClient
            .query(ListShopsQuery(input = Optional.presentIfNotNull(input)))
            .execute()
}