package com.test.searchapp.network.model

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("login")
    val loginUser: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("id")
    val id: Int
)