package com.api.project.config

import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.SslProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig {

    @Bean
    fun httpClient(): HttpClient {
        val sslContext = SslContextBuilder.forClient().sslProvider(SslProvider.JDK).build()
        return HttpClient.create().secure { sslProvider -> sslProvider.sslContext(sslContext) }
    }
}