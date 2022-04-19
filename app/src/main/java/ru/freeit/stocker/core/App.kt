package ru.freeit.stocker.core

import android.app.Application

class App : Application() {
    val module by lazy { CoreModule(this) }
}