package com.worldtech.awesomeandroidchart

class MyApplication : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        GlobeContext.context = applicationContext
    }
}