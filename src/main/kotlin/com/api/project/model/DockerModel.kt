package com.api.project.model

import com.api.project.model.enum.DockerStatus
import com.api.project.model.enum.FilesStatus
import org.springframework.data.annotation.TypeAlias
import java.time.LocalDateTime
import java.time.OffsetDateTime

@TypeAlias("DockerModel")
data class DockerModel(
    val id : String,
    val containerId: String,
    val containerName: String,
    val containerImage: String,
    val containerCommand : String,
    val containerStatus: DockerStatus = DockerStatus.CONTAINER_CREATED,
    val containerRealStatus: String,
    val containerPorts : String,
    val containerCreatedAt: String,
    val containerLogs : String,
    val lastModified: LocalDateTime = LocalDateTime.now(),
    val generatedAt: OffsetDateTime = OffsetDateTime.now()
)