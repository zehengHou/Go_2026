package com.example.domain.usecase

import com.example.domain.model.Result
import com.example.domain.model.User
import com.example.domain.repository.UserRepository

// Use Case - Single responsibility: Get user by ID
class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int): Result<User> {
        return try {
            val user = userRepository.getUserById(userId)
            if (user != null) {
                Result.Success(user)
            } else {
                Result.Error("User not found")
            }
        } catch (e: Exception) {
            Result.Error("Failed to fetch user", e)
        }
    }
}
