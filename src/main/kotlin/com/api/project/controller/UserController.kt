package com.api.project.controller

import com.api.project.controller.dto.response.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/user")
class UserController {

    @RequestMapping(method = [RequestMethod.GET], value = ["/{id}/page"])
    fun userPublicPage(@PathVariable("id") id: String): ResponseEntity<Any> {
        return ResponseEntity.ok(MessageResponse("Public profile"))
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["profile"])
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun userProfile(): String {
        return "User Content."
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["mod"])
    @PreAuthorize("hasRole('MODERATOR')")
    fun moderatorAccess(): String {
        return "Moderator Board."
    }

    @GetMapping("/admin")
    @RequestMapping(method = [RequestMethod.GET], value = ["admin"])
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return "Admin Board."
    }
}