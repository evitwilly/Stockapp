package ru.freeit.stocker.stock.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.core.view.components.StockCardView
import ru.freeit.stocker.core.view.components.StockLinearLayout
import ru.freeit.stocker.core.view.components.StockTextView

class ShimmingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(ShimmerFrameLayout(parent.context).apply {
    layoutParams = RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.MATCH_PARENT,
        RecyclerView.LayoutParams.WRAP_CONTENT
    ).apply {
        marginStart = context.dp(8)
        marginEnd = context.dp(8)
        bottomMargin = context.dp(8)
    }
    addView(StockCardView(context).apply {
        cardElevation = context.dp(8f)
        radius = context.dp(8f)
        setCardBackgroundColor(Color.rgb(200, 200, 200))
        addView(StockLinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(StockTextView(context).apply {
                setBackgroundColor(Color.rgb(22, 22, 22))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    context.dp(40)
                )
            }, StockTextView(context).apply {
                background = GradientDrawable().apply {
                    cornerRadius = context.dp(8f)
                    setColor(Color.rgb(66, 66, 66))
                }
                layoutParams = LinearLayout.LayoutParams(context.dp(120), context.dp(48)).apply {
                    marginStart = context.dp(8)
                    marginEnd = context.dp(8)
                    topMargin = context.dp(16)
                    bottomMargin = context.dp(16)
                }
            }, StockTextView(context).apply {
                background = GradientDrawable().apply {
                    cornerRadius = context.dp(4f)
                    setColor(Color.rgb(66, 66, 66))
                }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    context.dp(32)
                ).apply {
                    marginStart = context.dp(8)
                    marginEnd = context.dp(8)
                    bottomMargin = context.dp(16)
                }
            })

        })
    })
})