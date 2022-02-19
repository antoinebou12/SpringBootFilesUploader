package com.api.project.models

import com.api.project.models.enum.DockerStatus
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.OffsetDateTime

@TypeAlias("DockerModel")
@Document(collection = "docker")
data class DockerModel(
    val id: String,
    val containerId: String,
    val containerName: String,
    val containerImage: String,
    val containerCommand: String,
    val containerStatus: DockerStatus = DockerStatus.CONTAINER_CREATED,
    val containerRealStatus: String,
    val containerPorts: String,
    val containerCreatedAt: String,
    val containerLogs: String,
    val lastModified: LocalDateTime = LocalDateTime.now(),
    val generatedAt: OffsetDateTime = OffsetDateTime.now()
)