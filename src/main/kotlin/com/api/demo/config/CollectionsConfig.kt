package com.api.demo.config

import com.api.demo.model.BasicModel
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.Index

@Profile("!test")
@Configuration
class CollectionsConfig(private val mongoTemplate: MongoTemplate) {
    @EventListener(ApplicationReadyEvent::class)
    fun initIndexes() {
        //24h TTL
        mongoTemplate.indexOps(BasicModel::class.java).ensureIndex(Index().on("generatedAt", Sort.Direction.ASC).expire(86400))
    }
}