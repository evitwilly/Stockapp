package ru.freeit.stocker.stock.presentation.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import ru.freeit.stocker.R
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.core.view.colors.StockColors
import ru.freeit.stocker.core.view.components.StockTextView
import ru.freeit.stocker.core.view.layout.linearLayoutParams

class ErrorView(ctx: Context) : LinearLayout(ctx) {

    private val title = StockTextView(context)
    private val content = StockTextView(context)
    private val retry = Button(context)

    fun changeRetryListener(listener: () -> Unit) {
        retry.setOnClickListener { listener.invoke() }
    }

    fun changeError(titleText: String, contentText: String) {
        title.text = titleText
        content.text = contentText
    }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        padding(context.dp(16))

        title.fontSize(21f)
        title.gravity = Gravity.CENTER_HORIZONTAL
        title.typeface = title.open_sans_semi_bold
        title.layoutParams(linearLayoutParams()
            .marginTop(context.dp(32)).wrap())

        addView(title)

        addView(Space(context).apply {
            layoutParams(linearLayoutParams().matchWidth()
                .height(context.dp(16)))
        })

        content.fontSize(18f)
        content.gravity = Gravity.CENTER_HORIZONTAL
        content.typeface = content.open_sans_medium
        content.layoutParams(linearLayoutParams().wrap().marginBottom(context.dp(16)))

        addView(content)

        retry.fontSize(18f)
        retry.setText(R.string.try_again)
        retry.setTextColor(StockColors.white)
        retry.typeface = retry.open_sans_semi_bold
        retry.isAllCaps = false
        retry.backgroundTintList = ColorStateList.valueOf(StockColors.green500)
        retry.padding(context.dp(16))
        retry.layoutParams(linearLayoutParams().matchWidth().wrapHeight())

        addView(retry)

    }

}