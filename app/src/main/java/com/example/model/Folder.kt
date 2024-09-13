package com.example.model

data class Folder(
    val bucketId: Int?,
    val bucketName: String?,
    val coverImagePath: String?,
) {
    var imagesCount: Int = 0
        private set

    fun setImageCount(count: Int) {
        imagesCount = count
    }

    override fun toString(): String {
        return "ImageFolder(bucketId=$bucketId, bucketName=$bucketName, coverImagePath=$coverImagePath, imagesCount=$imagesCount)"
    }
}