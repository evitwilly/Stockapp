package ru.freeit.stocker.stock.ui

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.stocker.R
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.stock.ui.models.StockSymbol

class StockViewHolder(private val elements: StockViewHolderComponents) : RecyclerView.ViewHolder(elements.root) {

    fun bind(symbol: StockSymbol) {
        symbol.bindName(elements.symbol)
        symbol.bindDesc(elements.desc)
    }

    companion object {
        fun from(parent: ViewGroup) : StockViewHolder {
            val ctx = parent.context
            val root = StockCardView(ctx).apply {
                cardElevation = ctx.dp(8f)
                radius = ctx.dp(8f)
                layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginStart = ctx.dp(8)
                    marginEnd = ctx.dp(8)
                    bottomMargin = ctx.dp(8)
                }
            }
            val linear = StockLinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
            }
            root.addView(linear)

            val padding = ctx.dp(8)
            val symbol = StockTextView(ctx).apply {
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
            return StockViewHolder(StockViewHolderComponents(root, linear, symbol, desc))
        }
    }
}