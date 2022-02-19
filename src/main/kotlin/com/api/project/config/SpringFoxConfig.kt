package com.api.project.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.function.Predicate

// http://localhost:8080/swagger-ui/index.html
@Configuration
@EnableSwagger2
class SpringFoxConfig {
    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.SWAGGER_2).groupName("api")
            .apiInfo(apiInfo()).select().build()
    }

    private fun paths(): Predicate<String?>? {
        return regex("/*")
    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder().title("API Documentation")
            .description("API endpoint documentation for developper")
            .license("MIT License")
            .version("0.0.2").build()
    }
}