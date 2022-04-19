package ru.freeit.stocker.stock.presentation.adapter

import ru.freeit.stocker.core.view.components.StockCardView
import ru.freeit.stocker.core.view.components.StockLinearLayout
import ru.freeit.stocker.core.view.components.StockTextView

class StockViewHolderComponents(
    val root: StockCardView,
    val linear: StockLinearLayout,
    val symbol: StockTextView,
    val desc: StockTextView
)