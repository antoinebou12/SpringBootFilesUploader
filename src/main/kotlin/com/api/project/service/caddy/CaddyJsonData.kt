package com.api.project.service.caddy

import org.springframework.beans.factory.annotation.Value

data class CaddyConfig(
    val admin: CaddyAdmin? = CaddyAdmin(false),
    val storage: CaddyStorage? = CaddyStorage(),
    val apps: CaddyApps = CaddyApps(),
    val logging: CaddyLogging = CaddyLogging(),
    val tls: CaddyTLS = CaddyTLS()

)

data class CaddyAdmin(
    val disabled: Boolean? = false
)

//data class CaddyIdentity(
//
//)
//
//data class CaddyRemote(
//
//)
//


data class CaddyLogging(
    var `@id`: String? = "CaddyLogging",
    var logs: CaddyLogs? = CaddyLogs()
)

data class CaddyLogs(
    var `@id`: String? = "CaddyLogs",
    var default: CaddyLogsDefault? = CaddyLogsDefault()
)

data class CaddyLogsDefault(
    var `@id`: String? = "CaddyLogsDefault",
    var writer: CaddyLogsWriter = CaddyLogsWriter()
)

data class CaddyLogsWriter(
    var `@id`: String? = "CaddyLogsWriter",
    var filename: String = "log.log",
    var output: String = "file",
    var roll_keep: Int = 5,
    var roll_keep_days: Int = 7,
    var roll_size_mb: Int = 10
)

data class CaddyTLS(
    var `@id`: String? = "CaddyTLS",
    var automation: CaddyAutomation = CaddyAutomation()
)

data class CaddyAutomation(
    var `@id`: String? = "CaddyAutomation",
    var policies: List<CaddyPolicy> = listOf(CaddyPolicy())
)

data class CaddyPolicy(
    var `@id`: String? = "CaddyPolicy",
    var issuers: List<CaddyIssuer> = listOf(CaddyIssuer()),
    var subjects: List<String> = listOf("")
)

data class CaddyIssuer(
    var `@id`: String? = "CaddyIssuer",
    @Value("caddy.email") var email: String = "",
    var module: String = "acme"
)

data class CaddyApps(
    var `@id`: String? = "CaddyApps",
    var http: CaddyHttp? = CaddyHttp()
)

data class CaddyStorage(
    var `@id`: String? = "CaddyStorage",
    var module: String? = "",
    var root: String? = ""
)

data class CaddyHttp(
    var `@id`: String? = "CaddyHttp",
    var servers: CaddyServers? = CaddyServers()
)

data class CaddyServers(
    var `@id`: String? = "CaddyServers",
    var server: List<CaddyServer>? = listOf(CaddyServer())
)

data class CaddyServer(
    var `@id`: String? = "CaddyServer",
    var listen: List<String>? = listOf(":443"),
    var routes: List<CaddyRoute>? = listOf(CaddyRoute()),
)

data class CaddyRoute(
    var `@id`: String? = "CaddyRoute",
    var group: String? = "caddy",
    var handle: List<CaddyHandle>? = listOf(CaddyHandle()),
    var match: CaddyMatch? = CaddyMatch(),
    var terminal: Boolean? = true,
)

data class CaddyHandle(
    var `@id`: String? = "CaddyHandle",
    var handler: String? = "",
    var routes: List<CaddyRoute>? = listOf(CaddyRoute()),
    var body: String? = "",
    var providers: CaddyProvider? = CaddyProvider(),
    var upstreams: List<CaddyUpstream>? = listOf(CaddyUpstream())
)

data class CaddyUpstream(
    var `@id`: String? = "CaddyUpstream",
    var dial: String? = "",
    var lookup_srv: String? = "",
    var max_requests: Int = 3
)

data class CaddyMatch(
    var `@id`: String? = "CaddyMatch",
    var host: List<String>? = listOf(""),
    var path: String? = ""
)

data class CaddyProvider(
    var `@id`: String? = "CaddyProviders",
    var http_basic: CaddyHttpBasic? = CaddyHttpBasic(),
    var hash: CaddyHash? = CaddyHash()
)

data class CaddyHash(
    var `@id`: String? = "CaddyHash",
    var algorithm: String? = "bcrypt"
)

data class CaddyHttpBasic(
    var `@id`: String? = "CaddyHttpBasic",
    var accounts: List<CaddyAccount>? = listOf(CaddyAccount())
)

data class CaddyAccount(
    var `@id`: String? = "CaddyAccount",
    var username: String? = "",
    var password: String? = ""
)