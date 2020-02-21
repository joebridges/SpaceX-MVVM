package com.spacex_mvvm.ui.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImageWithGlide(view: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }
    Glide.with(view.context).load(url).into(view)
}