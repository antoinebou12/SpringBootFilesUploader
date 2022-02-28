package com.api.project.service.caddy

class CaddyConfigJsonMapper {
    private var config: CaddyConfig = CaddyConfig()

    private fun buildRoute(id: String, handler: String, dial: String? = "", body: String? = ""): CaddyRoute {
        val route = CaddyRoute(`@id` = id)
        when(handler){
                "subroute" -> {
                    route.handle?.get(0)?.handler = "subroute"
                    route.handle?.get(0)?.routes?.get(0)?.handle?.get(0)?.handler = "reverse_proxy"
                    route.handle?.get(0)?.upstreams?.get(0)?.dial = dial
                    route.handle?.get(0)?.providers = null
                }
                "static_response" -> {
                    route.handle?.get(0)?.handler = "static_response"
                    route.handle?.get(0)?.body = body
                    route.handle?.get(0)?.providers = null
                }
                "authentication" -> {
                    route.handle?.get(0)?.handler = "authentication"
                    route.handle?.get(0)?.providers?.http_basic?.accounts?.get(0)?.username = ""
                    route.handle?.get(0)?.providers?.http_basic?.accounts?.get(0)?.password = ""
                }
                else -> {
                    throw Exception("error")
                }
            }
            return route
        }

    private fun buildMatch(id: String, host: String): CaddyMatch {
        val match = CaddyMatch()
        match.host = listOf(host)
        return match
    }

    override fun toString(): String {
        return config.toString()
    }

}