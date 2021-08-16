package com.test.searchapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.searchapp.domain.model.User
import com.test.searchapp.network.GithubApiService
import com.test.searchapp.network.model.UserDtoMapper

class UsersPagingDataSource(
    private val githubApiService: GithubApiService,
    private val mapper: UserDtoMapper,
    private val query: String?
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val pageNumber = params.key ?: 0
            val response = githubApiService.getUserByName(
                query = query,
                maxLoad = params.loadSize,
                page = pageNumber
            )

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey =
                if (response.items.isNotEmpty() && !response.completeResult) pageNumber + 1 else null
            LoadResult.Page(
                data = mapper.mapToDomainModel(response.items),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}