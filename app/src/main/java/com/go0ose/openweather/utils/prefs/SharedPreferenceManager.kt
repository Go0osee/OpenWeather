package com.go0ose.openweather.utils.prefs

interface SharedPreferenceManager {
    fun getString(key: String): String
    fun saveString(key: String, value: String)
}