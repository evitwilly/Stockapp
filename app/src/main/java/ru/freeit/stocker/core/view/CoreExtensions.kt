package ru.freeit.stocker.core.view

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.freeit.stocker.core.view.layout.LP
import kotlin.math.roundToInt

fun ViewGroup.addView(vararg items: View) {
    for (item in items) {
        addView(item)
    }
}

fun TextView.fontSize(sp: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, sp)
}

fun Context.colorBy(@ColorRes resId: Int) : Int {
    return ContextCompat.getColor(this, resId)
}

fun Activity.dp(size: Int) = (resources.displayMetrics.density * size).roundToInt()
fun Context.dp(size: Int) = (resources.displayMetrics.density * size).roundToInt()
fun Context.dp(size: Float) = resources.displayMetrics.density * size

fun View.layoutParams(params: LP<*>) {
    layoutParams = params.build()
}

fun View.padding(start: Int = paddingStart, top: Int = paddingTop, end: Int = paddingEnd, bottom: Int = paddingBottom) {
    setPadding(start, top, end, bottom)
}

fun View.padding(horizontal: Int, vertical: Int) {
    padding(horizontal, vertical, horizontal, vertical)
}

fun View.padding(all: Int) {
    padding(all, all, all, all)
}