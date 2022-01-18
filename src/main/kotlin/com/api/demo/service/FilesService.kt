package com.api.demo.service

import com.api.demo.model.FileModel
import com.api.demo.repository.FileRepository
import com.api.demo.repository.FilesDAO
import org.springframework.stereotype.Service
import java.util.*


@Service
class FilesService(private val fileRepository: FileRepository, private val filesDAO: FilesDAO) {

    fun addDocument(document: ByteArray, fileName: String, extType: String, contentType: String, size: Long): FileModel {
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

    fun getExtType(fileName: String): String {
        var ext = ""

        val i = fileName.lastIndexOf('.')
        if (i > 0) {
            ext = fileName.substring(i + 1)
        }
        return ext
    }
}