package com.api.demo.config

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.errors.MinioException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@Configuration
class MinioConfig {

    @Value("\${minio.url}")
    private val minioUrl: String = ""
    @Value("\${minio.bucket}")
    private val minioBucket: String = ""
    @Value("\${minio.access-key}")
    private val minioAccessKey: String = ""
    @Value("\${minio.secret-key}")
    private val minioSecretKey: String = ""
    private var minioClient: MinioClient? = null

    fun connect(){
        if(minioClient == null) {
            try {
                // Create a minioClient with the MinIO server playground, its access key and secret key.
                minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build()

                // Make 'fus' bucket if not exist.
                val found = minioClient?.bucketExists(BucketExistsArgs.builder().bucket(minioBucket).build())
                if (!found!!) {
                    // Make a new bucket called 'fus'.
                    minioClient?.makeBucket(MakeBucketArgs.builder().bucket(minioBucket).build())
                } else {
                    println("Bucket '${minioBucket}' already exists.")
                }

            } catch (e: MinioException) {
                println("Error occurred: $e")
                println("HTTP trace: " + e.httpTrace())
            }
        }
    }

}