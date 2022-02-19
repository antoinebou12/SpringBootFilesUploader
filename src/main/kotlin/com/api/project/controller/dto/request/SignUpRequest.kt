package com.api.project.controller.dto.request

import org.springframework.data.annotation.TypeAlias
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size


@TypeAlias("SignUpRequest")
data class SignUpRequest(
    @field:NotEmpty
    @Size(min = 3, max = 20)
    val username: String,
    @field:NotEmpty
    @Size(max = 50)
    @Email
    val email: String,
    val roles: Set<String>,
    @field:NotEmpty
    @Size(min = 6, max = 40)
    val password: String
)