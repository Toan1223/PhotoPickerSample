package com.example.photopicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.repository.PhotoPickerRepository
import com.example.repository.impl.PhotoPickerRepositoryImpl

class PhotoPickerViewModel(
    photoPickerRepository: PhotoPickerRepository
) : ViewModel() {

    val images = photoPickerRepository.getImagesInFolder(null)

    companion object {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(PhotoPickerViewModel::class.java)) {
                    val application = checkNotNull(extras[APPLICATION_KEY])
                    val photoPickerRepository =
                        PhotoPickerRepositoryImpl(application.applicationContext)
                    return PhotoPickerViewModel(photoPickerRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}