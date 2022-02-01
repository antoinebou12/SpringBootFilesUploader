package com.api.project.controller.dto;

import org.springframework.data.annotation.TypeAlias
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotEmpty

@TypeAlias("BasicDTO")
data class BasicDTO(
    @field:NotEmpty
    val requestId: String,
    @field:NotEmpty
    val username: String = "",
    @field:DecimalMin(value = "0")
    @field:DecimalMax(value = "9999999999")
    val number: Number = 0,
    @field:NotEmpty
    val message: String = "",
)
