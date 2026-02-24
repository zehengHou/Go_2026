package com.example.data.repository

import com.example.data.datasource.MockUserDataSource
import com.example.domain.model.User
import com.example.domain.repository.UserRepository

// Repository Implementation - Implements Domain's interface
class UserRepositoryImpl(
    private val dataSource: MockUserDataSource
) : UserRepository {
    
    override suspend fun getUsers(): List<User> {
        return dataSource.fetchUsers()
    }
    
    override suspend fun getUserById(id: Int): User? {
        return dataSource.fetchUserById(id)
    }
}
