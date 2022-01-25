package com.api.demo.controller

import com.api.demo.controller.dto.FileDTO
import com.api.demo.service.DockerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/docker")
class DockerController(val dockerService: DockerService) {
    @GetMapping("/ping")
    fun getPing(): ResponseEntity<String> {
        dockerService.getPingDockerAPI()
        return ResponseEntity(
            "OK",
            HttpStatus.OK
        )
    }
}