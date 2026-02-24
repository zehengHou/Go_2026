package com.example.domain.repository

import com.example.domain.model.User

// Repository Interface - Domain Layer doesn't know about implementation details
interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Int): User?
}
