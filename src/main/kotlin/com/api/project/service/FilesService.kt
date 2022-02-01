package com.api.project.service

import com.api.project.model.FileModel
import com.api.project.repository.FileRepository
import com.api.project.repository.dao.FilesDAO
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import java.util.*


@Service
class FilesService(private val fileRepository: FileRepository, private val filesDAO: FilesDAO) {

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
            fileId = filesDAO.getNewFileId(),
            filePath = filePath,
            fileName = fileName,
            extType = extType,
            fileUUID = fileUUID,
            contentType = contentType,
            fileSize = size
        )
        filesDAO.saveFile(filesModel)
        return filesModel
    }

    fun getDocumentData(id: String): ByteArrayResource? {
        val document = filesDAO.getFile(id)?.filePath?.let { fileRepository.getDocument(it) }
        return document?.let { ByteArrayResource(it) }
    }

    fun getDocumentInfo(id: String): FileModel? {
        return filesDAO.getFile(id)
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