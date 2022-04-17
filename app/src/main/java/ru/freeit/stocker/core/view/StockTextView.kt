package ru.freeit.stocker.core.view

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.stocker.R

class StockTextView(ctx: Context) : AppCompatTextView(ctx) {

    init {
        setTextColor(context.colorBy(R.color.black))
    }

}