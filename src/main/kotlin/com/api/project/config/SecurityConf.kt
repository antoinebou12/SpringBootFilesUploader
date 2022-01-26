package com.api.project.config

import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.JwtDecoder


@Profile("!local")
@EnableWebSecurity
class SecurityConfiguration(private val jwtDecoder: JwtDecoder) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer {
                it.jwt { jwt -> jwt.decoder(jwtDecoder) }
            }
            .csrf()
            .disable()
    }
}
