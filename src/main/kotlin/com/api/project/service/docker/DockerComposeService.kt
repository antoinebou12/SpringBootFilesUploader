package com.api.project.service.docker

import com.api.project.repository.docker.DockerCompose
import com.api.project.repository.docker.DockerMongo
import org.springframework.stereotype.Service

@Service
class DockerComposeService(private val dockerCompose: DockerCompose, private val dockerMongo: DockerMongo) {



}
