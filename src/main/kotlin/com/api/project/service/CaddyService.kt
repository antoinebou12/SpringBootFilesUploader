package com.api.project.service

import com.api.project.repository.CaddyAPI
import com.api.project.repository.docker.DockerClientAPI
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CaddyService(private val caddyClient: CaddyAPI) {

    private val log = LoggerFactory.getLogger(DockerClientAPI::class.java)

    suspend fun getPingCaddy(): Boolean {
        return caddyClient.testConnection()
    }

}
