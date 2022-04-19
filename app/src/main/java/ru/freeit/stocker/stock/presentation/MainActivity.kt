package ru.freeit.stocker.stock.presentation

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.freeit.stocker.R
import ru.freeit.stocker.core.App
import ru.freeit.stocker.core.error.ErrorType
import ru.freeit.stocker.core.view.*
import ru.freeit.stocker.core.view.components.*
import ru.freeit.stocker.core.view.layout.frameLayoutParams
import ru.freeit.stocker.core.view.layout.horizontal
import ru.freeit.stocker.core.view.layout.linearLayoutParams
import ru.freeit.stocker.core.view.layout.vertical
import ru.freeit.stocker.stock.presentation.adapter.ShimmingAdapter
import ru.freeit.stocker.stock.presentation.adapter.StockAdapter
import ru.freeit.stocker.stock.presentation.helpers.Debounce
import ru.freeit.stocker.stock.presentation.models.StockState
import ru.freeit.stocker.stock.presentation.view.ErrorView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = colorBy(R.color.green_500)

        val viewModel = ViewModelProvider(this, (application as App).module.stockViewModelFactory())
            .get(StockViewModel::class.java)

        val linearLayoutRoot = StockLinearLayout(this).apply {
            vertical()
        }

        val toolbar = StockLinearLayout(this).apply {
            horizontal()
            gravity = Gravity.CENTER_VERTICAL
            layoutParams(linearLayoutParams().matchWidth().height(dp(72)).marginBottom(dp(8)))
            padding(dp(16))
        }
        linearLayoutRoot.addView(toolbar)

        val errorView = ErrorView(this).apply {
            layoutParams(linearLayoutParams().matchWidth().wrapHeight().weight(1f))
            isVisible = false
        }
        linearLayoutRoot.addView(errorView)

        val title = StockTextView(this).apply {
            typeface = open_sans_semi_bold
            setText(R.string.app_name)
            fontSize(23f)
            layoutParams(linearLayoutParams().wrap().weight(1f))
        }
        toolbar.addView(title)

        val searchFrameLayout = StockFrameLayout(this).apply {
            layoutParams(linearLayoutParams().wrapWidth().height(dp(72))
                .weight(1f).marginEnd(dp(8)))
            isVisible = false
        }
        toolbar.addView(searchFrameLayout)

        val searchEditText = StockEditText(this).apply {
            typeface = open_sans_medium
            isSingleLine = true
            maxLines = 1
            setHint(R.string.search)
            fontSize(23f)
            layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .gravity(Gravity.CENTER_VERTICAL or Gravity.START))
        }
        val debounce = Debounce()
        debounce.setup(searchEditText, viewModel::search)
        searchFrameLayout.addView(searchEditText)

        val closeSearchButton = StockImageButton(this).apply {
            setImageResource(R.drawable.ic_close_24)
            background = RippleDrawable(
                ColorStateList.valueOf(colorBy(R.color.green_500)),
                GradientDrawable().apply { cornerRadius = dp(50f); setColor(colorBy(R.color.white)) },
                null
            )
            layoutParams(frameLayoutParams().width(dp(32)).height(dp(32))
                .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
        }
        searchFrameLayout.addView(closeSearchButton)

        val searchButton = StockImageButton(this).apply {
            setImageResource(R.drawable.ic_search_24)
            background = RippleDrawable(
                ColorStateList.valueOf(colorBy(R.color.green_500)),
                GradientDrawable().apply { cornerRadius = dp(50f); setColor(colorBy(R.color.white)) },
                null
            )
            layoutParams(linearLayoutParams().width(dp(40)).height(dp(40)).marginEnd(dp(8)))
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

        val themeManager = (application as App).module.themeManager()
        val themeChangeButton = StockImageButton(this).apply {
            setImageResource(R.drawable.ic_dark_mode_24)
            setOnClickListener { themeManager.toggleTheme() }
            layoutParams(linearLayoutParams().width(dp(40)).height(dp(40)))
        }
        toolbar.addView(themeChangeButton)

        val stockList = StockRecyclerView(this)
        stockList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        stockList.layoutParams(linearLayoutParams().matchWidth().wrapHeight().weight(1f)
            .marginStart(dp(8)).marginEnd(dp(8)))
        linearLayoutRoot.addView(stockList)

        setContentView(linearLayoutRoot)

        viewModel.observe(this) { stockState ->
            errorView.isVisible = false
            stockList.isVisible = true

            when (stockState) {
                is StockState.Loading -> stockList.adapter = ShimmingAdapter(10, themeManager)
                is StockState.Success -> stockList.adapter = StockAdapter(stockState.items())
                is StockState.Error -> {
                    stockList.isVisible = false
                    errorView.isVisible = true
                    when (stockState.type) {
                        ErrorType.MISSING_INTERNET -> {
                            errorView.changeError(
                                getString(R.string.connection_error_title),
                                getString(R.string.connection_error_content)
                            )
                        }
                        ErrorType.SERVER_ERROR -> {
                            errorView.changeError(
                                getString(R.string.server_error_title),
                                getString(R.string.server_error_content)
                            )
                        }
                        ErrorType.HAPPEN_WHAT_IS_NOT_SO -> {
                            errorView.changeError(
                                getString(R.string.unknown_error_title),
                                getString(R.string.unknown_error_content)
                            )
                        }
                    }
                }
            }
        }

    }

}