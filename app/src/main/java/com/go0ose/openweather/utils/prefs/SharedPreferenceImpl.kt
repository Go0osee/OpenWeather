package com.go0ose.openweather.utils.prefs

import android.content.Context
import com.go0ose.openweather.utils.AppConstants.PREFS_NAME

class SharedPreferenceImpl(
    private val context: Context
) : SharedPreferenceManager {

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun getString(key: String): String {
        return prefs.getString(key, null).orEmpty()
    }

    override fun saveString(key: String, value: String) {
        prefs.edit()
            .putString(key, value)
            .apply()
    }
}