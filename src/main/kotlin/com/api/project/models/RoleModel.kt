package com.api.project.models

import com.api.project.models.enum.ERole
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@TypeAlias("RoleModel")
@Document(collection = "roles")
data class RoleModel(
    @Id
    val id: String?,
    @field:NotBlank
    val name: ERole
)
