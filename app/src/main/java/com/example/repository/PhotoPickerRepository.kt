package com.example.repository

import androidx.annotation.WorkerThread
import com.example.model.Folder
import com.example.model.Image
import kotlinx.coroutines.flow.Flow

interface PhotoPickerRepository {
    @WorkerThread
    fun getImagesInFolder(bucketId: Int?, emitItemCount: Int = 50): Flow<List<Image>>
}