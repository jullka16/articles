package com.example.articlesapp.tools

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class ImageLoader @Inject constructor(){

    fun load(context: Context, path: String, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }

}