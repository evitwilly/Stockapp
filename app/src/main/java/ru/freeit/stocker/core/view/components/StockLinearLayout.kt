package ru.freeit.stocker.core.view.components

import android.content.Context
import android.widget.LinearLayout
import ru.freeit.stocker.core.App
import ru.freeit.stocker.core.theme.Theme
import ru.freeit.stocker.core.view.colors.StockColors

open class StockLinearLayout(ctx: Context) : LinearLayout(ctx) {

    private val themeManager = (context.applicationContext as App).module.themeManager()

    private val listener: (Theme) -> Unit = { theme ->
        if (theme == Theme.LIGHT)
            setBackgroundColor(StockColors.white)
        else
            setBackgroundColor(StockColors.black)
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