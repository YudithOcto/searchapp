package com.test.searchapp.repository

import androidx.paging.PagingData
import com.test.searchapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsersByName(query: String?): Flow<PagingData<User>>
}