package com.example.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Shared elements transition extensions
 * */
infix fun <A : View, B : String> A.toSharedPair(that: B): Pair<A, B> =
    Pair(this.apply { transitionName = that }, that)

/**
 * Log extensions
 * */

fun String?.dLog(): String? {
    if (BuildConfig.DEBUG) {
        Log.d("ExampleLog ", this ?: "")
    }
    return this
}

fun String?.eLog() {
    if (BuildConfig.DEBUG) {
        Log.e("ExampleLog ", this ?: "")
    }
}

/**
 * View utils
 */
fun Fragment.hideKeyboard() {
    val activity = this.requireActivity()
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = activity.findViewById(android.R.id.content)
    }
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun ImageView.loadImage(url: String?) {
    if (url != null) {
        Glide
            .with(this.context)
            .load(url)
            .placeholder(R.drawable.currency_place_holder)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform()
            )
            .into(this)
    } else {
        this.setImageDrawable(this.context.getDrawable(R.drawable.currency_place_holder))
    }
}

fun <T> ArrayList<T>.swap(firstPosition: Int, secondPosition: Int) {
    val temp = this[firstPosition]
    this[firstPosition] = this[secondPosition]
    this[secondPosition] = temp
}


