package com.api.project.model

import org.springframework.data.annotation.TypeAlias

@TypeAlias("SSHModel")
data class SSHModel(
    val id: String,
    val name: String,
    val address: String,
    val port: String
)