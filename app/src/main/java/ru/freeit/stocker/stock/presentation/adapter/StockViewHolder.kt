package ru.freeit.stocker.stock.presentation.adapter

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.stocker.R
import ru.freeit.stocker.core.theme.Theme
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.core.view.components.StockListItemLinearLayout
import ru.freeit.stocker.core.view.components.StockTextView
import ru.freeit.stocker.core.view.layout.recyclerLayoutParams
import ru.freeit.stocker.core.view.layout.vertical
import ru.freeit.stocker.stock.presentation.models.StockSymbol

class StockViewHolder(private val elements: StockViewHolderComponents) : RecyclerView.ViewHolder(elements.root) {

    fun bind(symbol: StockSymbol) {
        symbol.bindName(elements.symbol)
        symbol.bindDesc(elements.desc)
    }

    companion object {
        fun from(parent: ViewGroup) : StockViewHolder {
            val ctx = parent.context
            val root = CardView(ctx).apply {
                val dimen = ctx.dp(8)
                cardElevation = dimen.toFloat()
                radius = dimen.toFloat()
                layoutParams(recyclerLayoutParams().matchWidth().wrapHeight()
                    .marginStart(dimen).marginEnd(dimen).marginBottom(dimen))
            }
            val linear = StockListItemLinearLayout(ctx).apply {
                vertical()
            }
            root.addView(linear)

            val padding = ctx.dp(8)
            val symbol = AppCompatTextView(ctx).apply {
                typeface = open_sans_semi_bold
                setBackgroundColor(ctx.colorBy(R.color.green_500))
                setTextColor(ctx.colorBy(R.color.white))
                setPadding(padding, padding, padding, padding)
                fontSize(21f)
            }
            val price = StockTextView(ctx).apply {
                fontSize(36f)
                typeface = open_sans_bold
                setPadding(padding, padding, padding, padding)
                text = "100$"
            }
            val desc = StockTextView(ctx).apply {
                typeface = open_sans_medium
                setPadding(padding, 0, padding, padding)
                fontSize(19f)
            }
            linear.addView(symbol, price, desc)
            return StockViewHolder(StockViewHolderComponents(root, symbol, desc))
        }
    }
}