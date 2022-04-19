package ru.freeit.stocker.stock.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import ru.freeit.stocker.core.theme.Theme
import ru.freeit.stocker.core.theme.ThemeManager
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.core.view.colors.StockColors
import ru.freeit.stocker.core.view.components.StockTextView
import ru.freeit.stocker.core.view.layout.linearLayoutParams
import ru.freeit.stocker.core.view.layout.recyclerLayoutParams
import ru.freeit.stocker.core.view.layout.vertical

class ShimmingViewHolder(parent: ViewGroup, themeManager: ThemeManager) : RecyclerView.ViewHolder(ShimmerFrameLayout(parent.context).apply {
    val eight = context.dp(8)
    val sixteen = context.dp(16)

    val symbolPlaceholder = StockTextView(context).apply {
        setBackgroundColor(Color.rgb(22, 22, 22))
        layoutParams(linearLayoutParams().matchWidth().height(context.dp(40)))
    }

    val pricePlaceholder = StockTextView(context).apply {
        background = GradientDrawable().apply {
            cornerRadius = context.dp(8f)
            setColor(Color.rgb(66, 66, 66))
        }
        layoutParams(linearLayoutParams().width(context.dp(120))
            .height(context.dp(48)).marginStart(eight)
            .marginEnd(eight).marginTop(sixteen).marginBottom(sixteen))
    }

    val descPlaceholder = StockTextView(context).apply {
        background = GradientDrawable().apply {
            cornerRadius = context.dp(4f)
            setColor(Color.rgb(66, 66, 66))
        }
        layoutParams(linearLayoutParams().matchWidth().height(context.dp(32))
            .marginStart(eight).marginEnd(eight).marginBottom(16))
    }

    val linearLayout = LinearLayout(context).apply {
        vertical()
        addView(symbolPlaceholder, pricePlaceholder, descPlaceholder)
    }

    val themeChangingListener : (Theme) -> Unit = { theme ->
        val symbolPlaceholderColor = if (theme == Theme.LIGHT)
            Color.rgb(22, 22, 22)
        else
            StockColors.white

        val pricePlaceholderColor = if (theme == Theme.LIGHT)
            Color.rgb(66, 66, 66)
        else
            StockColors.white

        val descPlaceholderColor = if (theme == Theme.LIGHT)
            Color.rgb(66, 66, 66)
        else
            StockColors.white

        val linearBackgroundColor = if (theme == Theme.LIGHT)
            Color.rgb(200, 200, 200)
        else
            Color.rgb(100, 100, 100)

        symbolPlaceholder.setBackgroundColor(symbolPlaceholderColor)
        pricePlaceholder.background = GradientDrawable().apply {
            cornerRadius = context.dp(8f)
            setColor(pricePlaceholderColor)
        }
        descPlaceholder.background = GradientDrawable().apply {
            cornerRadius = context.dp(4f)
            setColor(descPlaceholderColor)
        }
        linearLayout.setBackgroundColor(linearBackgroundColor)
    }

    val cardView = object: CardView(context) {
        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            themeManager.addThemeListener(themeChangingListener)
        }
        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            themeManager.removeThemeListener(themeChangingListener)
        }
    }.apply {
        cardElevation = context.dp(8f)
        radius = context.dp(8f)
        addView(linearLayout)
    }

    addView(cardView)
    layoutParams(recyclerLayoutParams().matchWidth().wrapHeight()
        .marginStart(eight).marginEnd(eight).marginBottom(eight))
})