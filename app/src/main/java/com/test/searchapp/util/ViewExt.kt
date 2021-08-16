package com.test.searchapp.util

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).thumbnail(0.2f).into(this)
}