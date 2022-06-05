package com.go0ose.openweather

import android.app.Application

class WeatherApplication: Application() {
    companion object {
        var appComponent: AppComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .buildContext(this)
            .build()
    }
}