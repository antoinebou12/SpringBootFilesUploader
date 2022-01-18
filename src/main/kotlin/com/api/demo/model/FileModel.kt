package com.api.demo.model

import com.api.demo.model.enum.FilesStatus
import org.springframework.data.annotation.TypeAlias
import java.time.LocalDateTime
import java.time.OffsetDateTime

@TypeAlias("FileModel")
data class FileModel(
    val fileId: String,
    val filePath: String,
    val fileName: String,
    val extType: String,
    val contentType: String,
    val fileUUID: String,
    val fileSize: Long,
    val fileStatus: FilesStatus = FilesStatus.FILE_CREATED,
    val lastModified: LocalDateTime = LocalDateTime.now(),
    val generatedAt: OffsetDateTime = OffsetDateTime.now()
)