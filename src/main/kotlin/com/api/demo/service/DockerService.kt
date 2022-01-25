package com.api.demo.service

import com.api.demo.repository.DockerDAO
import com.api.demo.repository.FileRepository
import com.api.demo.repository.FilesDAO
import org.springframework.stereotype.Service

@Service
class DockerService(private val dockerDAO: DockerDAO) {

    fun getPingDockerAPI(){
        return dockerDAO.testConnection()
    }
}