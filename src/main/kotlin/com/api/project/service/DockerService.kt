package com.api.project.service

import com.api.project.model.DockerModel
import com.api.project.repository.DockerClientAPI
import com.api.project.repository.dao.DockerDAO
import com.nimbusds.jose.shaded.json.JSONArray
import com.nimbusds.jose.shaded.json.JSONObject
import org.springframework.stereotype.Service


@Service
class DockerService(private val dockerClient: DockerClientAPI, private val dockerDAO: DockerDAO) {

    fun getPingDockerAPI() {
        return dockerClient.testConnection()
    }

    fun getListContainers(): JSONArray {
        val listContainerOriginal = dockerClient.listContainers()
        val containersInfo = JSONArray()

        listContainerOriginal.forEach{
            val container = JSONObject()

            container["id"] = it.id
            container["name"] = it.names[0].replace("/", "")
            container["image"] = it.image
            container["privatePort"] = it.ports.get(0).privatePort
            container["publicPort"] = it.ports.get(0).publicPort
            container["state"] = it.state
            container["status"] = it.status
            container["createdTimestamp"] = it.created
            container["createdDate"] = it.created
            container["command"] = it.command

            containersInfo.appendElement(container)
        }
        return containersInfo
    }


    fun createContainer(imageName: String): DockerModel? {
        dockerClient.pullContainer(imageName)?.awaitCompletion()
        dockerClient.createContainer(imageName)
        return null
    }

    fun startContainer(id: String): Void? {
        return dockerClient.startContainer(id)
    }

    fun stopContainer(id: String): Void? {
        return dockerClient.stopContainer(id)
    }

    fun restartContainer(id: String): Void? {
        return dockerClient.restartContainer(id)
    }

    fun renameContainer(id: String, name: String): Void? {
        return dockerClient.renameContainer(id, name)
    }

    fun inspectContainer(id: String): String? {
        dockerClient.inspectContainer(id)
        return null
    }



}