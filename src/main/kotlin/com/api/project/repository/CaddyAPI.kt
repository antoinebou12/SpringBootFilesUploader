package com.api.project.repository

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

    companion object CaddyConfigMapper {
        private var config: CaddyConfig = init()

//        private fun buildRoute(): CaddyRoutes {
//            return CaddyRoutes()
//        }

        private fun init(): CaddyConfig {
            return CaddyConfig(
                null,
                null,
                CaddyApps(
                    CaddyHttp(
                        CaddyServers(
                            listOf(
                                CaddyServer(
                                    listOf(":443"),
                                    CaddyRoutes(
                                        "caddy",
                                        listOf(CaddyHandle(
                                            "subroute",
                                            "",
                                            listOf(CaddyUpstreams(
                                                "",
                                                "",
                                                3
                                            ))
                                        )),
                                        CaddyMatch(
                                            "",
                                            ""
                                        ),
                                    true
                                )
                            )
                            )
                        )
                    )
                )
            )
        }

        override fun toString(): String {
            return config.toString()
        }

    }

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

    suspend fun testConnection(): Boolean {
        return true
    }

}


val caddyJsonConfigTemplate = """
{
    "admin": {
        "disabled": false,
        "listen": "",
        "enforce_origin": false,
        "origins": [""],
        "config": {
        "persist": false,
        "load": {•••}
    },
    "identity": {
        "identifiers": [""],
        "issuers": [{•••}]
    },
    "remote": {
        "listen": "",
        "access_control": [{
            "public_keys": [""],
            "permissions": [{
                "paths": [""],
                "methods": [""]
            }]
        }]
    }
    },
    "logging": {
        "sink": {
            "writer": {•••}
        },
        "logs": {
            "": {
                "writer": {•••},
                "encoder": {•••},
                "level": "",
                "sampling": {
                    "interval": 0,
                    "first": 0,
                    "thereafter": 0
                },
            "include": [""],
            "exclude": [""]
        }
    }
    },
    "storage": {•••},
    "apps": {
        "http": {
            "servers": {
                "example": {
                "listen": [":2015"],
                "routes": [{
                    "handle": [{
                        "handler": "static_response",
                        "body": "Hello, world!"
                    }]
                }]
                }
            }
        }
    }
}
"""


val caddyJsonConfigSimpleTemplate = """
{
    "apps": {
        "http": {
            "servers": {
                "example": {
                "listen": [":2015"],
                "routes": [{
                    "handle": [{
                        "handler": "static_response",
                        "body": "Hello, world!"
                    }]
                }]
                }
            }
        }
    }
}
"""

data class CaddyConfig(
    val admin: CaddyAdmin?,
    val storage: CaddyStorage?,
    val apps: CaddyApps

)

data class CaddyAdmin(
    val disabled: Boolean
)

//data class CaddyIdentity(
//
//)
//
//data class CaddyRemote(
//
//)
//
//data class CaddyLogs(
//
//)


data class CaddyApps(
    val http: CaddyHttp
)

data class CaddyStorage(
    val module: String,
    val root: String
)

data class CaddyHttp(
    val servers: CaddyServers
)

data class CaddyServers(
    val server: List<CaddyServer>
)

data class CaddyServer(
    val listen: List<String>? = listOf(":443"),
    val routes: CaddyRoutes,

)

data class CaddyRoutes(
    val group: String? = "caddy",
    val handle: List<CaddyHandle>,
    val match: CaddyMatch,
    val terminal: Boolean?,
)

data class CaddyHandle(
    val handler: String,
    val body: String?,
    val upstream: List<CaddyUpstreams>
)

data class CaddyUpstreams(
    val dial: String,
    val lookup_srv: String?,
    val max_requests: Int = 3
)

data class CaddyMatch(
    val host: String,
    val path: String?
)