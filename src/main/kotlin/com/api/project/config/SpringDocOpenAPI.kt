package com.api.project.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocOpenAPI {
    @Bean
    fun customOpenAPI(@Value("\${springdoc.version}") appVersion: String?): OpenAPI? {
        return OpenAPI()
            .components(Components())
            .info(Info().title("API").version(appVersion)
                    .license(License().name("MIT").url("https://github.com/antoinebou13/SpringBootFilesUploader"))
            )
    }
}