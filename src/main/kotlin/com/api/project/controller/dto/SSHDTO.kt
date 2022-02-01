package com.api.project.controller.dto

import org.springframework.data.annotation.TypeAlias
import javax.validation.constraints.NotEmpty

@TypeAlias("SSHDTO")
data class SSHDTO (
    val id: String?,
    val username: String,
    val password: String,
    val host: String,
    val port: Int,
    val command: String
)