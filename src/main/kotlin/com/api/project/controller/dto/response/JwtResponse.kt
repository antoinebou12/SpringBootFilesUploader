package com.api.project.controller.dto.response

import org.springframework.data.annotation.TypeAlias


@TypeAlias("JwtResponse")
data class JwtResponse(
    val accessToken: String,
    val username: String,
    val email: String,
    val roles: List<String>,
    val tokenType: String = "Bearer",
)