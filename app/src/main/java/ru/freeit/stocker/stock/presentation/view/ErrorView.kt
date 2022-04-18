package ru.freeit.stocker.stock.presentation.view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Space
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.core.view.layout.frameLayoutParams
import ru.freeit.stocker.core.view.layout.linearLayoutParams

class ErrorView(ctx: Context) : LinearLayout(ctx) {

    private val title = StockTextView(context)
    private val content = StockTextView(context)

    fun changeError(titleText: String, contentText: String) {
        title.text = titleText
        content.text = contentText
    }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        padding(context.dp(16))

        title.fontSize(21f)
        title.typeface = title.open_sans_semi_bold
        title.layoutParams(linearLayoutParams()
            .marginTop(context.dp(32)).wrap())

        addView(title)

        addView(Space(context).apply {
            layoutParams(linearLayoutParams().matchWidth()
                .height(context.dp(16)))
        })

        content.fontSize(18f)
        content.typeface = content.open_sans_medium
        content.layoutParams(linearLayoutParams().wrap())

        addView(content)

    }

}