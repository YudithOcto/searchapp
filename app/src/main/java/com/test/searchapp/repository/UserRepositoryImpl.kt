package com.test.searchapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.test.searchapp.network.GithubApiService
import com.test.searchapp.network.model.UserDtoMapper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: GithubApiService,
    private val mapper: UserDtoMapper
) : UserRepository {

    override fun getUsersByName(query: String?) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingDataSource(userService, mapper, query) }
        ).flow
}