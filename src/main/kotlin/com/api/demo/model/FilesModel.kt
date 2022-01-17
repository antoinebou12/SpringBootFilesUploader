package com.api.demo.model

import org.springframework.data.annotation.TypeAlias
import java.time.OffsetDateTime

@TypeAlias("FilesModel")
data class FilesModel(
    val id: String,
    val filename: String,
    val objectName: String,
    val extName: String,
    val generatedAt: OffsetDateTime = OffsetDateTime.now()
)