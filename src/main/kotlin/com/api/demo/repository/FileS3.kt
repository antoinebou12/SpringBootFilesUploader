package com.api.demo.repository

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class FileS3: FileRepository {
    @Autowired
    lateinit var awsClient: AmazonS3

    @Value("\${s3.bucket.name}")
    val s3BucketName: String = ""

    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener(ApplicationReadyEvent::class)
    fun autoCreateBucket() {
        if (awsClient.doesBucketExistV2(s3BucketName)) {
            log.info(("S3 Bucket \"$s3BucketName\" already exists."))
        } else {
            log.info(("S3 Bucket \"$s3BucketName\" does not exist. Creating bucket ..."))
            awsClient.createBucket(s3BucketName)
        }
    }

    override fun putPhoto(photoId: String, photo: ByteArray) {
        val metadata = ObjectMetadata()
        metadata.contentLength = photo.size.toLong()
        metadata.contentType = MediaType.IMAGE_JPEG_VALUE
        awsClient.putObject(PutObjectRequest(s3BucketName, photoId, photo.inputStream(), metadata))
    }

    override fun putDocument(documentId: String, document: ByteArray) {
        val metadata = ObjectMetadata()
        metadata.contentLength = document.size.toLong()
        awsClient.putObject(PutObjectRequest(s3BucketName, documentId, document.inputStream(), metadata))
    }


    override fun getPhoto(id: String): ByteArray? {
        return getObject(id)
    }

    override fun getPhotos(ids: Collection<String>): Map<String, ByteArray> {
        val objectsMap = mutableMapOf<String, ByteArray>()
        for (id in ids) {
            getObject(id)?.let {
                objectsMap[id] = it
            }
        }
        return objectsMap
    }

    fun getObject(id: String): ByteArray? {
        return try {
            log.debug("Fetching object id \"$id\" from S3 Bucket")
            val s3Object = awsClient.getObject(s3BucketName, id)
            IOUtils.toByteArray(s3Object.objectContent)
        } catch (ex: Exception) {
            log.error("Exception occured while fetching object id \"$id\" from S3 Bucket", ex)
            null
        }
    }

    override fun getDocument(id: String): ByteArray? {
        return getObject(id)
    }
}