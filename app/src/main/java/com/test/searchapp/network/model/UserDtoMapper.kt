package com.test.searchapp.network.model

import com.test.searchapp.domain.model.User
import com.test.searchapp.domain.DomainMapper

class UserDtoMapper : DomainMapper<List<UserResponseDto>, List<User>> {

    override fun mapToDomainModel(user: List<UserResponseDto>): List<User> {
        return user.map { item ->
            User(
                id = item.id,
                name = item.loginUser.orEmpty(),
                image = item.avatarUrl.orEmpty()
            )
        }
    }
}