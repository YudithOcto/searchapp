package com.test.searchapp.network.model

import com.google.gson.annotations.SerializedName

data class UserListResponseDto(
    val items: List<UserResponseDto>,
    @SerializedName("incomplete_results")
    val incompleteResult: Boolean
)