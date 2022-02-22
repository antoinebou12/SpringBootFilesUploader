package com.api.project.repository.docker

import com.api.project.models.DockerModel
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class DockerMongo(private val mongoTemplate: MongoTemplate) {

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