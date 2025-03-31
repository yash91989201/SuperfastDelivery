package com.example.core.network

import com.example.core.app_state.state_holder.ApplicationStateHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenRefreshInterceptor @Inject constructor(
    private val tokenRefresher: TokenRefresher,
    private val applicationStateHolder: ApplicationStateHolder
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code == 401) {
            response.close()

            val isRefreshed = runBlocking {
                tokenRefresher.refreshAccessToken()
            }

            if (isRefreshed) {
                val session = runBlocking {
                    applicationStateHolder.sessionStateHolder.session.first()
                }

                val newRequest = if (session != null) {
                    originalRequest.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer ${session.accessToken}")
                        .build()
                } else originalRequest

                return chain.proceed(newRequest)
            }
        }
        return response
    }
}