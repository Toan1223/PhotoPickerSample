package com.example.model

import android.net.Uri

data class Image(
    val id: Long,
    val uri: Uri,
    val path: String
)