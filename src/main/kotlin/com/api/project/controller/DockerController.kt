package com.api.project.controller

import com.api.project.model.DockerModel
import com.api.project.service.DockerService
import com.nimbusds.jose.shaded.json.JSONArray
import com.nimbusds.jose.shaded.json.JSONObject
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
    fun getListContainers(): ResponseEntity<JSONArray> {
        return ResponseEntity(
            dockerService.getListContainers(),
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/create"])
    fun createContainer(@RequestBody imageName: String): ResponseEntity<DockerModel> {
        return ResponseEntity(
            dockerService.createContainer(imageName),
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/start/{name}"])
    fun startContainer(@PathVariable("name") name: String): ResponseEntity<String> {
        dockerService.startContainer(name)
        return ResponseEntity(
            "Start Container $name",
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/stop/{name}"])
    fun stopContainer(@PathVariable("name") name: String): ResponseEntity<String> {
        dockerService.stopContainer(name)
        return ResponseEntity(
            "Stop Container $name",
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/restart/{name}"])
    fun restartContainer(@PathVariable("name") name: String): ResponseEntity<String> {
        dockerService.restartContainer(name)
        return ResponseEntity(
            "Restart Container $name",
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/rename/{name}/{newName}"])
    fun renameContainer(@PathVariable("name") name: String, @PathVariable("newName") newName: String): ResponseEntity<String> {
        dockerService.restartContainer(name)
        return ResponseEntity(
            "Rename Container $name $newName",
            HttpStatus.OK
        )
    }

}