package com.api.project.controller

import com.api.project.controller.dto.SSHDTO
import com.api.project.service.SSHService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ssh")
class SSHController(val sshService: SSHService) {

    @RequestMapping(method = [RequestMethod.POST], value = ["/cli"])
    @PreAuthorize("hasRole('ADMIN')")
    fun runCommand(@Validated @RequestBody sshDTO: SSHDTO): ResponseEntity<String> {
        val sshResponse =
            sshService.startSSH(sshDTO.username, sshDTO.password, sshDTO.host, sshDTO.port, sshDTO.command)
        return ResponseEntity(sshResponse, HttpStatus.OK)
    }
}
