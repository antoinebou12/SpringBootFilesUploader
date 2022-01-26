package com.api.project.repository

import com.api.project.model.DockerModel
import com.api.project.model.FileModel
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class DockerDAO(private val mongoTemplate: MongoTemplate) {

    fun saveDocker(dockerModel: DockerModel): DockerModel {
        return mongoTemplate.save(dockerModel)
    }

    fun removeDocker(id: String): DockerModel? {
        return mongoTemplate.findAndRemove(
            Query.query(
                (Criteria
                    .where("id").`is`(id))
            ), DockerModel::class.java
        )
    }
}