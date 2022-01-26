package com.api.project.config;

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import com.github.dockerjava.transport.DockerHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
class DockerConfig {

    @Value("\${docker.host}")
    val dockerHost = "";

    @Bean
    fun dockerClient(): DockerClient? {
        try {
            val dockerConfig: DockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                .withDockerTlsVerify(false)
//            .withDockerCertPath("/home/user/.docker")
//            .withRegistryUsername(registryUser)
//            .withRegistryPassword(registryPass)
//            .withRegistryEmail(registryMail)
//            .withRegistryUrl(registryUrl)
                .build()

            val httpClient: DockerHttpClient = ApacheDockerHttpClient.Builder()
                .dockerHost(dockerConfig.getDockerHost())
                .sslConfig(dockerConfig.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build()

            val dockerClient = DockerClientImpl.getInstance(dockerConfig, httpClient)

            dockerClient.pingCmd().exec();
            return dockerClient
        } catch (e: Exception) {
            System.out.println(e)
            return null;
        }

    }
}
