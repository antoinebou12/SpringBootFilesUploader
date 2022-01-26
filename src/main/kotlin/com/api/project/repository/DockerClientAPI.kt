package com.api.project.repository;

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.command.InspectContainerResponse
import com.github.dockerjava.api.command.RenameContainerCmd
import com.github.dockerjava.api.model.*
import com.github.dockerjava.core.command.LogContainerResultCallback
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class DockerClientAPI {

    private val log = LoggerFactory.getLogger(DockerClientAPI::class.java)

    @Autowired
    lateinit var dockerClient: DockerClient

    fun testConnection() {
        dockerClient.pingCmd().exec()
    }

    fun listContainers(): List<Container> {
        return dockerClient.listContainersCmd().exec()
    }

    fun createContainer(image: String): CreateContainerResponse? {
        return dockerClient.createContainerCmd(image).exec()
    }

    fun infoContainer(id: String): Info? {
        return dockerClient.infoCmd().exec()
    }

    fun dockerVersion(): Version? {
        return dockerClient.versionCmd().exec()
    }

    fun inspectContainer(id: String): InspectContainerResponse? {
        return dockerClient.inspectContainerCmd(id).exec()
    }

    fun getDockerLogs(id: String): List<String>? {
        val logs: MutableList<String> = ArrayList()
        val logContainerCmd = dockerClient.logContainerCmd(id)
        logContainerCmd.withStdOut(true).withStdErr(true)
        logContainerCmd.withTimestamps(true)
        try {
            logContainerCmd.exec(object : LogContainerResultCallback() {
                override fun onNext(item: Frame) {
                    logs.add(item.toString())
                }
            }).awaitCompletion()
        } catch (e: InterruptedException) {
            println("Interrupted Exception!" + e.message)
        }
        return logs
    }

    fun stopContainer(id: String): Void? {
        return dockerClient.stopContainerCmd(id).exec()
    }

    fun startContainer(id: String): Void? {
        return dockerClient.startContainerCmd(id).exec()
    }

    fun restartContainer(id: String): Void? {
        return dockerClient.restartContainerCmd(id).exec()
    }

    fun renameContainer(id: String, name: String): Void? {
        return dockerClient.renameContainerCmd(id).exec()
    }

    fun pruneSystem() {
        dockerClient.pruneCmd(PruneType.BUILD).exec()
        dockerClient.pruneCmd(PruneType.CONTAINERS).exec()
        dockerClient.pruneCmd(PruneType.IMAGES).exec()
        dockerClient.pruneCmd(PruneType.VOLUMES).exec()
        dockerClient.pruneCmd(PruneType.NETWORKS).exec()
    }


}
