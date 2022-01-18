package com.api.demo.repository;

interface FileRepository {

    fun getPhotos(ids: Collection<String>): Map<String, ByteArray>

    fun getPhoto(id: String): ByteArray?

    fun getDocument(id: String): ByteArray?

    fun putPhoto(photoId: String, photo: ByteArray)

    fun putDocument(documentId: String, document: ByteArray)
}