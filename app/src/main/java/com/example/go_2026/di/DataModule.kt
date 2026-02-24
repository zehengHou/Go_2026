package com.example.go_2026.di

import com.example.data.datasource.MockUserDataSource
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun provideMockUserDataSource(): MockUserDataSource {
        return MockUserDataSource()
    }
    
    @Provides
    @Singleton
    fun provideUserRepository(
        dataSource: MockUserDataSource
    ): UserRepository {
        return UserRepositoryImpl(dataSource)
    }
}
