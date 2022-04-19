package ru.freeit.stocker.core.view.layout


import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ru.freeit.noxml.layout.*

fun linearLayoutParams() = LinearLayoutLP()
fun frameLayoutParams() = FrameLayoutLP()
fun recyclerLayoutParams() = RecyclerViewLP()

fun LinearLayout.vertical() {
    orientation = LinearLayout.VERTICAL
}
fun LinearLayout.horizontal() {
    orientation = LinearLayout.HORIZONTAL
}
