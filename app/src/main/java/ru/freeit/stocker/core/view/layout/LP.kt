package ru.freeit.stocker.core.view.layout

import android.view.ViewGroup

interface LP<T : ViewGroup.LayoutParams> {
    fun build() : T
}