package ru.freeit.stocker.core.prefs

import android.content.Context

class AppPrefs(ctx: Context) : IntPrefs {

    private val prefs = ctx.getSharedPreferences(app_prefs, Context.MODE_PRIVATE)
    private val edit = prefs.edit()

    override fun int(key: String, default: Int): Int = prefs.getInt(key, default)
    override fun saveInt(key: String, value: Int) { edit.putInt(key, value).apply() }

    companion object {
        private const val app_prefs = "application_prefs"
    }
}