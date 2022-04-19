package ru.freeit.stocker.stock.presentation

import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.widget.doOnTextChanged

class Debounce {
    private val handler = Handler(Looper.getMainLooper())

    fun setup(editText: EditText, func: (str: String) -> Unit) {
        val runnableSearch = { func.invoke(editText.text.toString()) }
        editText.doOnTextChanged { _, _, _, _ ->
            handler.removeCallbacks(runnableSearch)
            handler.postDelayed(runnableSearch, 1000L)
        }
    }

}