package com.api.project.service.caddy

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import reactor.netty.http.client.HttpClient

@Component
class CaddyAPI(httpClient: HttpClient,
               @Value("caddy.host") host: String) {


    val webClient: WebClient = WebClient.builder()
        .clientConnector(ReactorClientHttpConnector(httpClient))
        .baseUrl(host)
        .build()

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun getConfig(): CaddyConfig {
        return webClient.get()
            .uri("/config").awaitExchange {
                if (it.statusCode().isError) {
                    val errorBody = it.awaitBody<String>()
                    logger.error("Error while calling caddy api: $errorBody")

                }
                it.awaitBody()
            }
    }

    suspend fun getConfig(path: String): Any {
        return webClient.get()
            .uri("/config/$path").awaitExchange {
                if (it.statusCode().isError) {
                    val errorBody = it.awaitBody<String>()
                    logger.error("Error while calling caddy api: $errorBody")

                }
                it.awaitBody()
            }
    }

    suspend fun getConfig(id: String, path: String): Any {
        return webClient.get()
            .uri("/id/$id/$path").awaitExchange {
                if (it.statusCode().isError) {
                    val errorBody = it.awaitBody<String>()
                    logger.error("Error while calling caddy api: $errorBody")

                }
                it.awaitBody()
            }
    }


    suspend fun loadConfig(caddyConfig: CaddyConfig): Any {

        val bytes = caddyConfig.toString().toByte()

        return webClient.post()
            .uri("/config")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .bodyValue(bytes)
            .awaitExchange {
                if (it.statusCode().isError) {
                    val errorBody = it.awaitBody<String>()
                    logger.error("Error while calling caddy api: $errorBody")

                }
                it.awaitBody()
            }
    }

    suspend fun loadConfig(path: String, data: Byte): Any {

        return webClient.post()
            .uri("/config/$path")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .bodyValue(data)
            .awaitExchange {
                if (it.statusCode().isError) {
                    val errorBody = it.awaitBody<String>()
                    logger.error("Error while calling caddy api: $errorBody")

                }
                it.awaitBody()
            }
    }

    suspend fun testConnection(): Boolean {
        return true
    }

}