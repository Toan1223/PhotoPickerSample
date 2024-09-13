package com.example.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.model.Image
import com.example.photopicker.databinding.ItemPhotoBinding

class PhotoAdapter :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    private val images: ArrayList<Image> by lazy {
        arrayListOf()
    }

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.PhotoViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(images: List<Image>) {
        Log.d("TAG", "addDataPhoto: ${images.size}")
        if (this.images.isEmpty()) {
            this.images.addAll(images)
            notifyDataSetChanged()
        } else {
            val startIndex = this.images.size + 1
            this.images.addAll(images)
            notifyItemRangeInserted(startIndex, images.size)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, position: Int) {
        holder.onBind(images[position])
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Image) {
            Glide.with(binding.root.context)
                .load(item.path)
                .into(binding.imgPhoto)
        }

    }
}