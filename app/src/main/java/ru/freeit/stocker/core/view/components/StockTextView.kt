package ru.freeit.stocker.core.view.components

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.stocker.core.App
import ru.freeit.stocker.core.theme.Theme
import ru.freeit.stocker.core.view.colors.StockColors

class StockTextView(ctx: Context) : AppCompatTextView(ctx) {

    private val themeManager = (context.applicationContext as App).module.themeManager()

    private val listener: (Theme) -> Unit = { theme ->
        if (theme == Theme.LIGHT)
            setTextColor(StockColors.black)
        else
            setTextColor(StockColors.white)
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