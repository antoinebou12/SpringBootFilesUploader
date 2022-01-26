package com.api.project.service

import com.api.project.model.DockerModel
import com.api.project.repository.DockerClientAPI
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.command.InspectContainerResponse
import org.springframework.stereotype.Service

@Service
class DockerService(private val dockerClient: DockerClientAPI) {

    fun getPingDockerAPI() {
        return dockerClient.testConnection()
    }

    fun getListContainers(): String {
        return dockerClient.listContainers().toString()
    }

    fun createContainer(imageName: String): DockerModel? {
        dockerClient.createContainer(imageName)
        return null
    }

    fun startContainer(id: String): Void? {
        return dockerClient.startContainer(id)
    }

    fun stopContainer(id: String): Void? {
        return dockerClient.startContainer(id)
    }

    fun restartContainer(id: String): Void? {
        return dockerClient.startContainer(id)
    }

//    fun renameContainer(id: String, name: String): Void? {
//        return dockerClient.renameContainer(id, name)
//    }

    fun inspectContainer(id: String): String? {
        dockerClient.inspectContainer(id)
        return null
    }

}