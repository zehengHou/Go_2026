package com.example.domain.usecase

import com.example.domain.model.Result
import com.example.domain.model.User
import com.example.domain.repository.UserRepository

// Use Case - Single responsibility: Get all users
class GetUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> {
        return try {
            val users = userRepository.getUsers()
            Result.Success(users)
        } catch (e: Exception) {
            Result.Error("Failed to fetch users", e)
        }
    }
}
