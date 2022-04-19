package ru.freeit.stocker.core.theme

import ru.freeit.stocker.core.prefs.IntPrefs

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