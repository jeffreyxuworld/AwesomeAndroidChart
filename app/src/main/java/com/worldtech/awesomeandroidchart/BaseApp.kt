package com.worldtech.awesomeandroidchart

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.Utils
import kotlin.properties.Delegates

open class BaseApp :Application(),ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        var appContext: Context by Delegates.notNull()
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        mAppViewModelStore = ViewModelStore()
        Utils.init(this)
    }

    /** 获取一个全局的ViewModel */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}