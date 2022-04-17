package ru.freeit.stocker.stock.ui

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.freeit.stocker.R
import ru.freeit.stocker.core.App
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.stock.ui.models.StockState

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = colorBy(R.color.green_500)

        val viewModel = ViewModelProvider(this, (application as App).module.stockViewModelFactory())
            .get(StockViewModel::class.java)

        val linearLayoutRoot = StockLinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val toolbar = StockLinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(72)
            ).apply {
                bottomMargin = dp(8)
            }
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }
        linearLayoutRoot.addView(toolbar)

        val title = StockTextView(this).apply {
            typeface = open_sans_semi_bold
            fontSize(23f)
            setText(R.string.app_name)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
            }
        }
        toolbar.addView(title)

        val searchFrameLayout = StockFrameLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                dp(72)
            ).apply {
                weight = 1f
                marginEnd = dp(8)
            }
            isVisible = false
        }
        toolbar.addView(searchFrameLayout)



        val searchEditText = AppCompatEditText(this).apply {
            typeface = open_sans_medium
            isSingleLine = true
            maxLines = 1
            fontSize(23f)
            setHint(R.string.search)
            setPadding(paddingStart, paddingTop, dp(40), paddingBottom)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_VERTICAL or Gravity.START
            }
        }

        val runnableSearch = { viewModel.search(searchEditText.text.toString()) }
        val handler = Handler(Looper.getMainLooper())
        searchEditText.doOnTextChanged { _, _, _, _ ->
            handler.removeCallbacks(runnableSearch)
            handler.postDelayed(runnableSearch, 1000L)
        }

        searchFrameLayout.addView(searchEditText)

        val closeSearchButton = StockImageButton(this).apply {
            setImageResource(R.drawable.ic_close_24)
            background = RippleDrawable(
                ColorStateList.valueOf(colorBy(R.color.green_500)),
                GradientDrawable().apply { cornerRadius = dp(50f); setColor(colorBy(R.color.white)) },
                null
            )
            layoutParams = FrameLayout.LayoutParams(dp(32), dp(32)).apply {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
            }
        }
        searchFrameLayout.addView(closeSearchButton)

        val searchButton = StockImageButton(this).apply {
            setImageResource(R.drawable.ic_search_24)
            background = RippleDrawable(
                ColorStateList.valueOf(colorBy(R.color.green_500)),
                GradientDrawable().apply { cornerRadius = dp(50f); setColor(colorBy(R.color.white)) },
                null
            )
            layoutParams = LinearLayout.LayoutParams(dp(40), dp(40)).apply {
                marginEnd = dp(8)
            }
            setOnClickListener {
                isVisible = false
                title.isVisible = false
                searchFrameLayout.isVisible = true
                searchFrameLayout.startAnimation(ScaleAnimation(0f, 1f, 1f, 1f, title.measuredWidth.toFloat(), 0f).apply {
                    duration = 200L
                })
            }
        }
        toolbar.addView(searchButton)

        closeSearchButton.setOnClickListener {
            searchButton.isVisible = true
            title.isVisible = true
            searchFrameLayout.isVisible = false
            searchEditText.setText("")
            title.startAnimation(ScaleAnimation(0f, 1f, 1f, 1f, 0f, 0f).apply {
                duration = 200L
            })
        }

        val themeChangeButton = StockImageButton(this).apply {
            setImageResource(R.drawable.ic_dark_mode_24)
            background = RippleDrawable(
                ColorStateList.valueOf(colorBy(R.color.green_500)),
                GradientDrawable().apply { cornerRadius = dp(50f); setColor(colorBy(R.color.white)) },
                null
            )
            layoutParams = LinearLayout.LayoutParams(dp(40), dp(40))
        }
        toolbar.addView(themeChangeButton)

        val stockList = StockRecyclerView(this)
        val adapter = StockAdapter()
        stockList.adapter = adapter
        stockList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        stockList.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            weight = 1f
            marginStart = dp(8)
            marginEnd = dp(8)
        }
        linearLayoutRoot.addView(stockList)

        setContentView(linearLayoutRoot)

        viewModel.observe(this) { stockState ->
            when (stockState) {
                is StockState.Loading -> adapter.loading()
                is StockState.Success -> adapter.success(stockState.items)
            }
        }

    }

}