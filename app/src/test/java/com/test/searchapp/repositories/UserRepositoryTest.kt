package com.test.searchapp.repositories

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.whenever
import com.test.searchapp.domain.model.User
import com.test.searchapp.network.GithubApiService
import com.test.searchapp.network.model.UserDtoMapper
import com.test.searchapp.network.model.UserListResponseDto
import com.test.searchapp.network.model.UserResponseDto
import com.test.searchapp.repository.UsersPagingDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @Mock
    lateinit var githubApiService: GithubApiService

    @Mock
    lateinit var mapper: UserDtoMapper

    lateinit var usersPagingDataSource: UsersPagingDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        usersPagingDataSource = UsersPagingDataSource(githubApiService, mapper, "")
    }

    @Test
    fun `Failed api call 404`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(githubApiService.getUserByName(any(), any(), any())).willThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, User>(error)
        assertEquals(
            expectedResult, usersPagingDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Success api call return empty data`() = runBlockingTest {
        given(githubApiService.getUserByName(any(), any(), any())).willReturn(emptyUserResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = mapper.mapToDomainModel(emptyUserResponse.items),
            prevKey = null,
            nextKey = null
        )
        assertEquals(
            expectedResult, usersPagingDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Success api call have data but isComplete true`() = runBlockingTest {
        whenever(githubApiService.getUserByName(any(), any(), any())).thenReturn(userListResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = mapper.mapToDomainModel(userListResponse.items),
            prevKey = null,
            nextKey = null
        )
        assertEquals(
            expectedResult, usersPagingDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Success api call and have next data`() = runBlockingTest {
        given(githubApiService.getUserByName(any(), any(), any())).willReturn(userListPaginated)

        // prev key = 0 because we are starting the pagination call from 0
        val expectedResult = PagingSource.LoadResult.Page(
            data = mapper.mapToDomainModel(userListPaginated.items),
            prevKey = 0,
            nextKey = 2
        )

        assertEquals(expectedResult, usersPagingDataSource.load(PagingSource.LoadParams.Append(
            key = 1,
            loadSize = 10,
            placeholdersEnabled = false
        )))
    }

    @Test
    fun `Success api call and load previous data`() = runBlockingTest {
        given(githubApiService.getUserByName(any(), any(), any())).willReturn(userListPaginated)

        // prev key = 0 because we are starting the pagination call from 0
        val expectedResult = PagingSource.LoadResult.Page(
            data = mapper.mapToDomainModel(userListPaginated.items),
            prevKey = null,
            nextKey = 1
        )

        assertEquals(expectedResult, usersPagingDataSource.load(PagingSource.LoadParams.Append(
            key = 0,
            loadSize = 10,
            placeholdersEnabled = false
        )))
    }

    companion object {
        private val userListResponse = UserListResponseDto(
            listOf(
                UserResponseDto("test_user", "avatar_user", 1)
            ), true
        )

        private val emptyUserResponse = UserListResponseDto(
            listOf(), true
        )

        private val userListPaginated = UserListResponseDto(
            listOf(
                UserResponseDto("test_user", "avatar_user", 1)
            ), false
        )

    }

}