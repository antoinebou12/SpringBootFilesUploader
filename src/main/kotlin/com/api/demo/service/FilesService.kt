package com.api.demo.service

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.UploadObjectArgs
import io.minio.errors.MinioException
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service


@Service
@EnableConfigurationProperties
class FilesService() {

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

    fun uploadFile(filename: String, objectName: String ) {
        if (minioClient != null) {
            minioClient?.uploadObject(
                UploadObjectArgs.builder()
                    .bucket(minioBucket)
                    .`object`(objectName)
                    .filename(filename)
                    .build()
            )
            println(
                "$filename is successfully uploaded as object $objectName to bucket $minioBucket."
            )
        }
    }

}