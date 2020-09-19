package com.gao.sunnyweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "TAkhjf8d1nlSlspN"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}