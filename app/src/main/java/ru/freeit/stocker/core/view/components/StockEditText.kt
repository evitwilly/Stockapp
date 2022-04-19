package ru.freeit.stocker.core.view.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.appcompat.widget.AppCompatEditText
import ru.freeit.stocker.core.App
import ru.freeit.stocker.core.theme.Theme
import ru.freeit.stocker.core.view.colors.StockColors
import ru.freeit.stocker.core.view.dp
import ru.freeit.stocker.core.view.padding

class StockEditText(ctx: Context) : AppCompatEditText(ctx) {
    init {
        val inset = context.dp(2)
        background = InsetDrawable(
            StateListDrawable().apply {
                addState(intArrayOf(android.R.attr.state_focused), GradientDrawable().apply {
                    setStroke(inset, StockColors.green700)
                })
                addState(StateSet.WILD_CARD, GradientDrawable().apply {
                    setStroke(inset, StockColors.green500)
                })
            }, -inset, -inset, -inset, 0
        )
        val padd = context.dp(4)
        padding(padd, padd, context.dp(40), padd)
    }

    private val themeManager = (context.applicationContext as App).module.themeManager()

    private val listener: (Theme) -> Unit = { theme ->
        if (theme == Theme.DARK)
            setTextColor(StockColors.white)
        else
            setTextColor(StockColors.black)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.addThemeListener(listener)
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.removeThemeListener(listener)
    }
}