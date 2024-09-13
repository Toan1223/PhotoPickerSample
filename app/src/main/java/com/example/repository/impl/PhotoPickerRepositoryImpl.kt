package com.example.repository.impl

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.example.model.Folder
import com.example.model.Image
import com.example.repository.PhotoPickerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class PhotoPickerRepositoryImpl(private val context: Context) : PhotoPickerRepository {

    private val imageExtension = arrayOf("image/png", "image/jpg", "image/jpeg", "image/webp")
    private val selectionMimetype = "${MediaStore.Images.Media.MIME_TYPE} " +
            "IN (${"? ".repeat(imageExtension.size).trim().replace(" ", ",")})"

    private fun isFileExisted(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists() && file.length() > 0
    }

    /**
     * @param bucketId Folder's bucket id
     * @param emitItemCount Max image item can be emitted
     */
    @WorkerThread
    override fun getImagesInFolder(
        bucketId: Int?,
        emitItemCount: Int,
    ): Flow<List<Image>> {
        val imageList = mutableListOf<Image>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
        )

        val selection: String?
        val selectionArgs: Array<String>?
        if (bucketId != null) {
            selection =
                "${MediaStore.Images.Media.BUCKET_ID} = ? AND $selectionMimetype"
            selectionArgs = arrayOf(bucketId.toString()) + imageExtension
        } else {
            selection = selectionMimetype
            selectionArgs = imageExtension
        }
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        val contentResolver: ContentResolver = context.contentResolver
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        return flow {
            cursor?.use {
                val idColumnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val pathColumnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                while (it.moveToNext()) {
                    val path = it.getString(pathColumnIndex)
                    if (isFileExisted(path).not()) {
                        continue
                    }
                    val id = it.getLong(idColumnIndex)
                    val imageUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    //TODO read data return from cursor and emit to flow
                }
            }
        }
    }
}