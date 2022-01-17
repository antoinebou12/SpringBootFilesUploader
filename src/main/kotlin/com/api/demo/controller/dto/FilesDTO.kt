package com.api.demo.controller.dto

import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotEmpty

data class FilesDTO(
    @field:NotEmpty
    val id: String,
    @field:NotEmpty
    val filename: String = "",
    @field:NotEmpty
    val objectName: String = "",
    @field:NotEmpty
    val extName: String = "",
)