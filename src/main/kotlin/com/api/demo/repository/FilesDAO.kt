package com.api.demo.repository

import com.api.demo.model.FileModel
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class FilesDAO(private val mongoTemplate: MongoTemplate) {

    fun saveFile(filesModel: FileModel): FileModel {
        return mongoTemplate.save(filesModel)
    }

    fun getFile(id: String): FileModel? {
        return mongoTemplate.findOne(
            Query.query(
                (Criteria
                    .where("fileId").`is`(id))
            ), FileModel::class.java
        )
    }

    fun getFileByFileName(fileName: String): FileModel? {
        return mongoTemplate.findOne(
            Query.query(
                (Criteria
                    .where("fileName").`is`(fileName))
            ), FileModel::class.java
        )
    }

    fun getFileByUUID(uuid: String): FileModel? {
        return mongoTemplate.findOne(
            Query.query(
                (Criteria
                    .where("fileUUID").`is`(uuid))
            ), FileModel::class.java
        )
    }

    fun removeFile(id: String): FileModel? {
        return mongoTemplate.findAndRemove(
            Query.query(
                (Criteria
                    .where("fileId").`is`(id))
            ), FileModel::class.java
        )
    }

    fun getNewFileId(): String {
        return (mongoTemplate.count(Query(), FileModel::class.java) + 1).toString()
    }
}