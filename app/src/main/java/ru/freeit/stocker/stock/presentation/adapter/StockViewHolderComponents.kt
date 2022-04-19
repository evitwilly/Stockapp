package ru.freeit.stocker.stock.presentation.adapter

import android.widget.TextView
import androidx.cardview.widget.CardView
import ru.freeit.stocker.core.view.components.StockLinearLayout
import ru.freeit.stocker.core.view.components.StockTextView

class StockViewHolderComponents(
    val root: CardView,
    val symbol: TextView,
    val desc: TextView
)