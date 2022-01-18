package com.api.demo.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.multipart.support.MultipartFilter


@Configuration
class S3Config {

    @Value("\${s3.access.key}")
    val s3AccessKey: String = ""

    @Value("\${s3.secret.key}")
    val s3SecretKey: String = ""

    @Value("\${s3.service.endpoint}")
    val s3ServiceEndpoint: String = ""

    @Bean
    fun awsClient(): AmazonS3 {
        val credentials = BasicAWSCredentials(s3AccessKey, s3SecretKey)

        return AmazonS3ClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(s3ServiceEndpoint, Regions.US_EAST_1.name))
            .withPathStyleAccessEnabled(true)
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build()
    }
}