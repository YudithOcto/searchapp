package com.test.searchapp.network

import com.test.searchapp.network.model.UserListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    suspend fun getUserByName(
        @Query("q", encoded = true) query: String?,
        @Query("per_page") maxLoad: Int,
        @Query("page") page: Int
    ): UserListResponseDto
}
