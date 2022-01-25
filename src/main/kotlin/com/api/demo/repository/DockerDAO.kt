package com.api.demo.repository;

import com.github.dockerjava.api.DockerClient
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class DockerDAO {

    @Autowired
    lateinit var dockerClient: DockerClient

    fun testConnection() {
       dockerClient.pingCmd().exec();
    }

    fun testConnection() {
        dockerClient.pingCmd().exec();
    }
}
