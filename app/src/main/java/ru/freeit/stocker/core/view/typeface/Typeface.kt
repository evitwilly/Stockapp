package ru.freeit.stocker.core.view

import android.graphics.Typeface
import android.view.View

private val typefaceCache = hashMapOf<String, Typeface>()

private const val openSansRegular = "OpenSans-Regular.ttf"
val View.open_sans_regular: Typeface
    get() {
        if (!typefaceCache.containsKey(openSansRegular)) {
            val font = Typeface.createFromAsset(resources.assets, openSansRegular)
            typefaceCache[openSansRegular] = font
        }
        return typefaceCache[openSansRegular]!!
    }

private const val openSansMedium = "OpenSans-Medium.ttf"
val View.open_sans_medium: Typeface
    get() {
        if (!typefaceCache.containsKey(openSansMedium)) {
            val font = Typeface.createFromAsset(resources.assets, openSansMedium)
            typefaceCache[openSansMedium] = font
        }
        return typefaceCache[openSansMedium]!!
    }

private const val openSansSemiBold = "OpenSans-SemiBold.ttf"
val View.open_sans_semi_bold: Typeface
    get() {
        if (!typefaceCache.containsKey(openSansSemiBold)) {
            val font = Typeface.createFromAsset(resources.assets, openSansSemiBold)
            typefaceCache[openSansSemiBold] = font
        }
        return typefaceCache[openSansSemiBold]!!
    }

private const val openSansBold = "OpenSans-Bold.ttf"
val View.open_sans_bold: Typeface
    get() {
        if (!typefaceCache.containsKey(openSansBold)) {
            val font = Typeface.createFromAsset(resources.assets, openSansBold)
            typefaceCache[openSansBold] = font
        }
        return typefaceCache[openSansBold]!!
    }