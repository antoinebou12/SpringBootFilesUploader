package com.api.project.repository.user

import com.api.project.models.UserModel
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface UserRepository : MongoRepository<UserModel, String> {
    fun findByUsername(username: String): Optional<UserModel>
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}