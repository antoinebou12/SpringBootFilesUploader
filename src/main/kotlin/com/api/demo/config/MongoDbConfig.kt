package com.api.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

@Configuration
class MongoDbConfig {
    @Bean
    fun mongoCustomConversions(): MongoCustomConversions = MongoCustomConversions(
        listOf(
            DateToOffsetDateTimeConverter.INSTANCE,
            OffsetDateTimeToDateConverter.INSTANCE
        )
    )
}

@ReadingConverter
enum class DateToOffsetDateTimeConverter : Converter<Date, OffsetDateTime> {
    INSTANCE;

    override fun convert(source: Date): OffsetDateTime {
        return source.toInstant().atOffset(ZoneOffset.UTC)
    }
}

@WritingConverter
enum class OffsetDateTimeToDateConverter : Converter<OffsetDateTime, Date> {
    INSTANCE;

    override fun convert(source: OffsetDateTime): Date? {
        return Date.from(source.toInstant())
    }
}
