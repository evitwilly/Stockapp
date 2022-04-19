package ru.freeit.stocker.core.theme

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color

enum class Theme(bgColor: Int) {
    LIGHT(Color.rgb(255, 255, 255)),
    DARK(Color.rgb(0, 0, 0))
}

interface IntPrefs {
    fun int(key: String, default: Int) : Int
    fun saveInt(key: String, value: Int)
}

class AppPrefs(ctx: Context) : IntPrefs {

    private val prefs = ctx.getSharedPreferences(app_prefs, MODE_PRIVATE)
    private val edit = prefs.edit()

    override fun int(key: String, default: Int): Int = prefs.getInt(key, default)
    override fun saveInt(key: String, value: Int) { edit.putInt(key, value).apply() }

    companion object {
        private const val app_prefs = "application_prefs"
    }
}

class ThemeManager(private val prefs: IntPrefs) {

    private var currentTheme = Theme.LIGHT
    private val listeners = mutableSetOf<(theme: Theme) -> Unit>()

    init {
        currentTheme = Theme.values()[prefs.int(themeKey, 0)]
    }

    fun addThemeListener(listener: (Theme) -> Unit) {
        listeners.add(listener)
        listener.invoke(currentTheme)
    }
    fun removeThemeListener(listener: (Theme) -> Unit) { listeners.remove(listener) }

    fun toggleTheme() {
        val newTheme = if (currentTheme == Theme.LIGHT) Theme.DARK else Theme.LIGHT
        prefs.saveInt(themeKey, newTheme.ordinal)
        currentTheme = newTheme
        listeners.forEach { it.invoke(newTheme) }
    }

    companion object {
        private const val themeKey = "theme_manager_theme_key"
    }

}