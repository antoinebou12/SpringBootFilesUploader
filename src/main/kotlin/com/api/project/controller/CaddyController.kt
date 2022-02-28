package com.api.project.controller

import com.api.project.service.caddy.CaddyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/caddy")
class CaddyController(val caddyService: CaddyService) {

    @RequestMapping(method = [RequestMethod.GET], value = ["/ping"])
    suspend fun getPing(): ResponseEntity<String> {
        caddyService.getPingCaddy()
        return ResponseEntity(
            "OK",
            HttpStatus.OK
        )
    }
}