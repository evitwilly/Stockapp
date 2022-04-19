package ru.freeit.stocker.stock.presentation.adapter

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.core.view.colors.StockColors
import ru.freeit.stocker.core.view.components.StockListItemLinearLayout
import ru.freeit.stocker.core.view.components.StockTextView
import ru.freeit.stocker.core.view.layout.recyclerLayoutParams
import ru.freeit.stocker.core.view.layout.vertical
import ru.freeit.stocker.stock.presentation.StockViewModel
import ru.freeit.stocker.stock.presentation.models.StockSymbol

class StockViewHolder(
    private val elements: StockViewHolderComponents,
    private val coroutineScope: CoroutineScope,
    private val viewModel: StockViewModel
) : RecyclerView.ViewHolder(elements.root) {
    private val webSocketListener = viewModel.webSocketListener()
    private var resultNotifier: ResultNotifier? = null

    fun bind(symbol: StockSymbol) {
        symbol.bindName(elements.symbol)
        symbol.bindDesc(elements.desc)
        symbol.bindPrice(elements.price)

        webSocketListener.subscribeBySymbol(symbol.name())
        resultNotifier?.let { notifier -> webSocketListener.removeResultNotifier(notifier) }
        val resultNotifier : ResultNotifier = { symbolsAndPrices ->
            val newPrice = symbolsAndPrices[symbol.name()] ?: 0f
            if (newPrice > 0f) {
                symbol.changePrice(newPrice)
                symbol.bindPrice(elements.price)
                coroutineScope.launch {
                    viewModel.savePrice(symbol)
                }
            }
        }
        this.resultNotifier = resultNotifier
        webSocketListener.addResultNotifier(resultNotifier)
        coroutineScope.launch {
            val newPrice = viewModel.price(symbol)
            if (newPrice > 0f) {
                symbol.changePrice(newPrice)
                symbol.bindPrice(elements.price)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup, coroutineScope: CoroutineScope, viewModel: StockViewModel) : StockViewHolder {
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
                setBackgroundColor(StockColors.green500)
                setTextColor(StockColors.white)
                setPadding(padding, padding, padding, padding)
                fontSize(21f)
            }

            val price = StockTextView(ctx).apply {
                fontSize(36f)
                typeface = open_sans_bold
                setPadding(padding, padding, padding, padding)
            }

            val desc = StockTextView(ctx).apply {
                typeface = open_sans_medium
                setPadding(padding, 0, padding, padding)
                fontSize(19f)
            }
            linear.addView(symbol, price, desc)
            return StockViewHolder(StockViewHolderComponents(root, symbol, desc, price), coroutineScope, viewModel)
        }
    }
}