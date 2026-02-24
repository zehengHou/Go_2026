package com.example.go_2026.di

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetUserByIdUseCase
import com.example.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    
    @Provides
    @Singleton
    fun provideGetUsersUseCase(
        repository: UserRepository
    ): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(
        repository: UserRepository
    ): GetUserByIdUseCase {
        return GetUserByIdUseCase(repository)
    }
}
