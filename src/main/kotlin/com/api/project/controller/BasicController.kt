package com.api.project.controller

import com.api.project.controller.dto.BasicDTO
import com.api.project.model.BasicModel
import com.api.project.service.BasicService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/")
class BasicController(val basicService: BasicService) {

    @RequestMapping(method = [RequestMethod.DELETE], value = ["/{id}"])
    fun removeBasicInfo(@PathVariable("id") requestId: String): String {
        val deleteBasic = basicService.removeBasicRequest(requestId)
        return deleteBasic?.id ?: "Error"
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/{id}"])
    fun getBasicInfo(@PathVariable("id") requestId: String): ResponseEntity<BasicDTO>? {
        val savedBasic = basicService.getBasicRequest(requestId)
        return ResponseEntity(
            savedBasic?.let { itBasic ->
                BasicDTO(
                    requestId = itBasic.id,
                    username = itBasic.username,
                    number = itBasic.number,
                    message = itBasic.message
                )
            },
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/"])
    fun requestBasicInfo(@Validated @RequestBody basicDTO: BasicDTO): ResponseEntity<BasicDTO>? {
        val savedBasic = basicService.getBasicRequest(
            BasicModel(
                id = basicDTO.requestId,
                username = basicDTO.username,
                number = basicDTO.number,
                message = basicDTO.message
            )
        )
        return ResponseEntity(
            savedBasic?.let { it ->
                BasicDTO(
                    requestId = it.id,
                    username = it.username,
                    number = it.number,
                    message = it.message
                )
            },
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/add"])
    fun addBasic(@Validated @RequestBody basicDTO: BasicDTO): ResponseEntity<BasicDTO> {
        val basic = basicService.putNewBasic(basicDTO.requestId, basicDTO.username, basicDTO.number, basicDTO.message)
        return ResponseEntity(BasicDTO(basic.id, basic.username, basic.number, basic.message), HttpStatus.OK)
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/modify"])
    fun modifyBasic(@Validated @RequestBody basicDTO: BasicDTO): ResponseEntity<BasicDTO> {
        val basic = basicService.modifyBasic(
            BasicModel(
                basicDTO.requestId,
                basicDTO.username,
                basicDTO.number,
                basicDTO.message
            )
        )
        return if (basic != null) {
            ResponseEntity(
                BasicDTO(
                    requestId = basicDTO.requestId,
                    username = basicDTO.username,
                    number = basicDTO.number,
                    message = basicDTO.message
                ), HttpStatus.OK
            )
        } else {
            ResponseEntity(
                null, HttpStatus.OK
            )
        }
    }

    @RequestMapping(method = [RequestMethod.PATCH], value = ["/modify/{id}"])
    fun modifyPartialBasic(
        @PathVariable("id") requestId: String,
        @Validated @RequestBody username: String
    ): ResponseEntity<BasicDTO> {
        val basic = basicService.modifyPartialBasic(requestId, "username", username)
        return ResponseEntity(
            basic?.let { it ->
                BasicDTO(
                    requestId = it.id,
                    username = it.username,
                    number = it.number,
                    message = it.message
                )
            },
            HttpStatus.OK
        )
    }

    @RequestMapping(method = [RequestMethod.PATCH], value = ["/modify/{id}/{key}"])
    fun modifyGeneralPartialBasic(
        @PathVariable("id") requestId: String,
        @PathVariable("key") key: String,
        @Validated @RequestBody value: String
    ): ResponseEntity<BasicDTO> {
        val basic = basicService.modifyPartialBasic(requestId, key, value)
        return ResponseEntity(
            basic?.let { it ->
                BasicDTO(
                    requestId = it.id,
                    username = it.username,
                    number = it.number,
                    message = it.message
                )
            },
            HttpStatus.OK
        )
    }
}