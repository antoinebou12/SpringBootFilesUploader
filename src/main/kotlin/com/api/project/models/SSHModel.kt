package com.api.project.models

import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@TypeAlias("SSHModel")
@Document(collection = "ssh")
data class SSHModel(
    val id: String,
    val name: String,
    val address: String,
    val port: String
)