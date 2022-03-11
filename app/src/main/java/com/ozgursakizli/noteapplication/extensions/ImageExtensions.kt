package com.ozgursakizli.noteapplication.extensions

import android.widget.ImageView
import com.ozgursakizli.noteapplication.glidemodule.GlideApp

fun ImageView.displayImage(resId: Int?, placeHolder: Int) {
    GlideApp.with(this).load(resId).error(placeHolder).placeholder(placeHolder).into(this)
}

fun ImageView.displayImage(imagePath: String?, placeHolder: Int) {
    GlideApp.with(this).load(imagePath).error(placeHolder).placeholder(placeHolder).into(this)
}

fun ImageView.displayCircularImage(imagePath: String?, placeHolder: Int) {
    GlideApp.with(this)
        .load(imagePath)
        .circleCrop()
        .error(placeHolder)
        .placeholder(placeHolder)
        .into(this)
}