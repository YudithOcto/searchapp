package com.test.searchapp.di

import com.test.searchapp.network.GithubApiService
import com.test.searchapp.network.model.UserDtoMapper
import com.test.searchapp.repository.UserRepository
import com.test.searchapp.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(mapper: UserDtoMapper, apiService: GithubApiService): UserRepository {
        return UserRepositoryImpl(apiService, mapper)
    }

    @Provides
    @Singleton
    fun provideMapper() = UserDtoMapper()
}