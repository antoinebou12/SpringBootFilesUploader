package com.api.demo.repository

import com.api.demo.model.BasicModel
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class BasicDAO(private val mongoTemplate: MongoTemplate) {

    fun saveBasic(basicModel: BasicModel): BasicModel {
        return mongoTemplate.save(basicModel)
    }

    fun getBasic(requestId: String): BasicModel? {
        return mongoTemplate.findOne(Query.query((Criteria
            .where("id").`is`(requestId))), BasicModel::class.java)
    }

    fun removeBasic(requestId: String): BasicModel? {
        return mongoTemplate.findAndRemove(Query.query((Criteria
            .where("id").`is`(requestId))), BasicModel::class.java)
    }
    fun findAndModifiedBasic(basicModel: BasicModel): BasicModel? {
        val query = Query()
            .addCriteria(Criteria.where("id").`is`(basicModel.id))
        val update = Update()
            .set("username", basicModel.username)
            .set("number", basicModel.number)
            .set("message", basicModel.message)

        return mongoTemplate.findAndModify(query, update, FindAndModifyOptions().returnNew(true), BasicModel::class.java)
    }

    fun findAndModifiedPartialBasic(id:String, key: String, value: Any): BasicModel? {
        val query = Query()
            .addCriteria(Criteria.where("id").`is`(id))
        val update = Update()
            .set(key, value)

        return mongoTemplate.findAndModify(query, update, FindAndModifyOptions().returnNew(true), BasicModel::class.java)
    }
}
