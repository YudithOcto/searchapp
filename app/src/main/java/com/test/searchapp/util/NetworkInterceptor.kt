package com.test.searchapp.util

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request
            .newBuilder()
            .addHeader("Accept", "application/vnd.github.v3+json")
            .addHeader("Authorization", "token $TOKEN")
            .build()

        return chain.proceed(newRequest)
    }
}