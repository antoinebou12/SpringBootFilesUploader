package com.api.demo.controller

import com.api.demo.controller.dto.FilesDTO
import com.api.demo.service.FilesService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/files")
class FilesController(val filesService: FilesService){

    @PostMapping("/upload")
    fun uploadFile(@Validated @RequestBody filesDTO: FilesDTO): String {
        filesService.connect()
        filesService.uploadFile(filesDTO.filename, filesDTO.objectName)
        return "ok"
    }

}