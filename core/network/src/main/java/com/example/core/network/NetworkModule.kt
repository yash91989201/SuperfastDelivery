package com.example.core.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object NetworkModule {
    fun createOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        tokenRefreshInterceptor: TokenRefreshInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(tokenRefreshInterceptor)
            .addInterceptor(logging)
            .build()
    }

    fun createApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("http://192.168.1.6:8081/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }
}