package com.api.project.controller.dto.request

import org.springframework.data.annotation.TypeAlias
import javax.validation.constraints.NotEmpty

@TypeAlias("LoginRequest")
data class LoginRequest(
    @field:NotEmpty
    val username: String,
    @field:NotEmpty
    val password: String
)