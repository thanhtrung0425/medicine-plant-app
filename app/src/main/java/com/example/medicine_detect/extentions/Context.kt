package com.example.medicine_detect.extentions

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.example.medicine_detect.R
import com.example.medicine_detect.model.MedicinePlant
import com.example.medicine_detect.utils.apiutils.APIClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.ResourceSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

fun Context.getStateView(color: Int): ColorStateList {
    val colors =
        intArrayOf(color, ContextCompat.getColor(this, R.color.color_grey_2))

    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        ), colors
    )
}
