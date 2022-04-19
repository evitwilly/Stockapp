package ru.freeit.stocker.core.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.appcompat.widget.AppCompatImageButton
import ru.freeit.stocker.core.App
import ru.freeit.stocker.core.theme.Theme

class StockImageButton(ctx: Context) : AppCompatImageButton(ctx) {

    private val themeManager = (context.applicationContext as App).module.themeManager()

    private val listener: (Theme) -> Unit = { theme ->
        background = RippleDrawable(
            ColorStateList.valueOf(StockColors.green500),
            GradientDrawable().apply {
                cornerRadius = context.dp(50f)
                if (theme == Theme.LIGHT) setColor(StockColors.white)
                else setColor(StockColors.black)
            }, null
        )
        imageTintList = ColorStateList.valueOf(
            if (theme == Theme.LIGHT) StockColors.black else StockColors.white
        )
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