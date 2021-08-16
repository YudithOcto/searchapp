package com.test.searchapp.domain

interface DomainMapper <T, User> {

    fun mapToDomainModel(user: T): User
}