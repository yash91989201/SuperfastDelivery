package com.example.core.network

import com.example.core.app_state.state_holder.ApplicationStateHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val applicationStateHolder: ApplicationStateHolder
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val session = runBlocking {
            applicationStateHolder.sessionStateHolder.session.first()
        }

        val modifiedRequest = if (session != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${session.accessToken}")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(modifiedRequest)
    }
}