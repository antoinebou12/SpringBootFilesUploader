package com.api.project.model

import org.springframework.data.annotation.TypeAlias
import java.time.OffsetDateTime

@TypeAlias("BasicModel")
data class BasicModel(
    val id: String,
    val username: String,
    val number: Number,
    val message: String,
    val generatedAt: OffsetDateTime = OffsetDateTime.now(),
)
