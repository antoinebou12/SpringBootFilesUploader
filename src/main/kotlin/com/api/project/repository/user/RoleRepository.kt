package com.api.project.repository.user

import com.api.project.models.RoleModel
import com.api.project.models.enum.ERole
import org.springframework.data.mongodb.repository.MongoRepository


interface RoleRepository : MongoRepository<RoleModel, String> {
    fun findByName(name: ERole): RoleModel?
}