package com.api.project.controller.dto

import com.api.project.model.enum.DockerStatus
import org.springframework.data.annotation.TypeAlias
import javax.validation.constraints.NotEmpty

@TypeAlias("DockerDTO")
data class DockerDTO(
    @field:NotEmpty
    val id: String,
    @field:NotEmpty
    val containerId: String,
    val containerName: String,
    val containerImage: String,
    val containerStatus: DockerStatus,
    val containerPorts : String,
    val containerCreatedAt: String,
)