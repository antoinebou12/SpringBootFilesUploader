package com.api.demo.repository

import com.api.demo.model.FilesModel
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class FilesDAO(private val mongoTemplate: MongoTemplate) {

    fun saveFile(filesModel: FilesModel): FilesModel {
        return mongoTemplate.save(filesModel)
    }

    fun getFile(id: String): FilesModel? {
        return mongoTemplate.findOne(
            Query.query((Criteria
            .where("id").`is`(id))), FilesModel::class.java)
    }

    fun removeFile(id: String): FilesModel? {
        return mongoTemplate.findAndRemove(
            Query.query((Criteria
            .where("id").`is`(id))), FilesModel::class.java)
    }
}