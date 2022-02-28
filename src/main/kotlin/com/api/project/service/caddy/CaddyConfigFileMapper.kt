package com.api.project.service.caddy

class CaddyConfigFileMapper {
    fun buildLogs(path: String, email: String): String {
        return """
        {
            log {
                output file $path {
                    roll_size 10mb
                    roll_keep 5
                    roll_keep_for 168h
                }
            }
            email $email
        }
        """.trimIndent()
    }

    fun buildReverseProxy(domain: String, dial: String, path: String, basicAuth: String? = ""): String {
        return """
        $domain {
            $basicAuth
            reverse_proxy $path $dial
        }
        """.trimIndent()
    }

    fun buildBasicAuth(username: String, password: String): String{
        return """
        basicauth {
            $username $password
        }
        """.trimIndent()
    }

    fun buildFileServer(domain: String, path: String): String {
        return """
        $domain {
            root * $path
            file_server browse
        }
        """.trimIndent()
    }

    fun buildRedir(domain: String, dial: String): String {
        return """
        $domain {
            redir $dial{uri}
        }
        """.trimIndent()
    }

    fun buildRewrite(domain: String, originalURI: String, NewURI: String): String {
        return """
        $domain {
            rewrite $originalURI $NewURI
        }
        """.trimIndent()
    }
}