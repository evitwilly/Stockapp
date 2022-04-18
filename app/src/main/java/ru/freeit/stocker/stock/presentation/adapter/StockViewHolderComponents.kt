package ru.freeit.stocker.stock.presentation.adapter

import ru.freeit.stocker.core.view.StockCardView
import ru.freeit.stocker.core.view.StockLinearLayout
import ru.freeit.stocker.core.view.StockTextView

class StockViewHolderComponents(
    val root: StockCardView,
    val linear: StockLinearLayout,
    val symbol: StockTextView,
    val desc: StockTextView
)