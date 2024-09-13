package com.example.photopicker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adapter.PhotoAdapter
import com.example.photopicker.databinding.ActivityPhotoPickerBinding

class PhotoPickerActivity : AppCompatActivity() {

    private val viewModel: PhotoPickerViewModel by viewModels { PhotoPickerViewModel.factory }
    private val binding by lazy {
        ActivityPhotoPickerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adapter = PhotoAdapter()
        binding.rvPhoto.adapter = adapter
        //TODO Collect data from viewModel and submit to adapter
    }
}