package com.api.project.controller

import com.api.project.model.DockerModel
import com.api.project.service.DockerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/containers")
class DockerController(val dockerService: DockerService) {

    @RequestMapping(method = [RequestMethod.GET], value = ["/ping"])
    fun getPing(): ResponseEntity<String> {
        dockerService.getPingDockerAPI()
        return ResponseEntity(
            "OK",
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/list"])
    fun getListContainers(): ResponseEntity<String> {
        return ResponseEntity(
            dockerService.getListContainers(),
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/create"])
    fun createContainer(@RequestBody imageName: String): ResponseEntity<DockerModel> {
        return ResponseEntity(
            dockerService.createContainer("hello-world"),
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/start/{name}"])
    fun startContainer(@PathVariable("name") name: String): ResponseEntity<String> {
        return ResponseEntity(
            dockerService.getListContainers(),
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/stop/{name}"])
    fun stopContainer(@PathVariable("name") name: String): ResponseEntity<String> {
        return ResponseEntity(
            dockerService.getListContainers(),
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/restart/{name}"])
    fun restartContainer(@PathVariable("name") id: String): ResponseEntity<String> {
        return ResponseEntity(
            dockerService.getListContainers(),
            HttpStatus.OK
        )
    }

}