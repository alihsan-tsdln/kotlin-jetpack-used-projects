package com.tasdelen.besinlerkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tasdelen.besinlerkitabi.R

fun ImageView.getImage(url : String?) {

    Glide.with(context)
        .load(url)
        .placeholder(returnProgressBar(context))
        .error(R.drawable.ic_launcher_background)
        .into(this)
}

fun returnProgressBar(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view : ImageView, url: String?) {
    view.getImage(url)
}