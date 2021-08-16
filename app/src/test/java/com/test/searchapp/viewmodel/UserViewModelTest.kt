package com.test.searchapp.viewmodel

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.given
import com.test.searchapp.domain.model.User
import com.test.searchapp.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @Mock
    lateinit var repository: UserRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmptyUsers() = runBlockingTest {
        given(repository.getUsersByName(anyString())).willReturn(flowOf(PagingData.empty()))
        val expected = PagingData.empty<User>()
        assertEquals(expected, repository.getUsersByName("").first())
    }

    @Test
    fun getUsersList() = runBlockingTest {
        val expected = PagingData.from(userList)
        given(repository.getUsersByName(anyString())).willReturn(flowOf(expected))
        val userPaging = repository.getUsersByName("").singleOrNull()
        assertEquals(expected, userPaging)
    }

    companion object {
        private val userList = listOf(User(1, "test_user", "avatar_user"))
    }
}