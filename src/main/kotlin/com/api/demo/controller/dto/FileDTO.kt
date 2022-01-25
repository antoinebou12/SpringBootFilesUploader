package com.api.demo.controller.dto

import org.springframework.data.annotation.TypeAlias
import javax.validation.constraints.NotEmpty

@TypeAlias("FileDTO")
data class FileDTO(
    @field:NotEmpty
    val fileId: String,
    @field:NotEmpty
    val fileName: String,
)