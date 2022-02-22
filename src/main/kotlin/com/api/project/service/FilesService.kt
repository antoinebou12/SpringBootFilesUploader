package com.api.project.service

import com.api.project.models.FileModel
import com.api.project.repository.file.FileRepository
import com.api.project.repository.file.FilesMongo
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import java.util.*


@Service
class FilesService(private val fileRepository: FileRepository, private val filesMongo: FilesMongo) {

    fun addDocument(
        document: ByteArray,
        fileName: String,
        extType: String,
        contentType: String,
        size: Long
    ): FileModel {

        val fileUUID = UUID.randomUUID().toString()
        val filePath = "files/${fileUUID}/${fileName}"
        fileRepository.putDocument(filePath, document)

        val filesModel = FileModel(
            fileId = filesMongo.getNewFileId(),
            filePath = filePath,
            fileName = fileName,
            extType = extType,
            fileUUID = fileUUID,
            contentType = contentType,
            fileSize = size
        )
        filesMongo.saveFile(filesModel)
        return filesModel
    }

    fun getDocumentData(id: String): ByteArrayResource? {
        val document = filesMongo.getFile(id)?.filePath?.let { fileRepository.getDocument(it) }
        return document?.let { ByteArrayResource(it) }
    }

    fun getDocumentInfo(id: String): FileModel? {
        return filesMongo.getFile(id)
    }

    fun getExtType(fileName: String): String {
        var ext = ""

        val i = fileName.lastIndexOf('.')
        if (i > 0) {
            ext = fileName.substring(i + 1)
        }
        return ext
    }
}