package com.worldtech.awesomeandroidchart

import android.content.Context
import android.util.AttributeSet
import com.scwang.smart.refresh.header.ClassicsHeader

class CustomClassicsHeader : ClassicsHeader {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    fun setTextDesc(): ClassicsHeader {
        mTextPulling = "下拉刷新"
        mTextRelease = "释放更新"
        mTextRefreshing = "加载中..."
        mTextFinish = ""
        return this
    }
}