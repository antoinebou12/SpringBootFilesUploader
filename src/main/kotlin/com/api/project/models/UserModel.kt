package com.api.project.models

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


@Document(collection = "users")
@TypeAlias("UserModel")
data class UserModel(
    @field:NotBlank
    @Size(max = 20)
    var username: String,
    @field:NotBlank
    @Size(max = 50)
    @Email
    val email: String,
    @field:NotBlank
    @Size(max = 120)
    val password: String,
    @DBRef
    var roles: Set<RoleModel> = HashSet()

)