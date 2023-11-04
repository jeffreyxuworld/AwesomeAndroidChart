package com.worldtech.awesomeandroidchart.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.worldtech.awesomeandroidchart.R

class PkAdapter : BaseQuickAdapter<Float, BaseViewHolder> {
    constructor() : super(R.layout.item_pk_view)
    override fun convert(holder: BaseViewHolder, item: Float) {
        val postion = getItemPosition(item);
        val progress_bar_bg = holder.getView<View>(R.id.progress_bar_bg)
        val progress_bar = holder.getView<View>(R.id.progress_bar)
        val tv_name = holder.getView<TextView>(R.id.tv_name)
        val tv_num = holder.getView<TextView>(R.id.tv_num)
        progress_bar_bg.post {
            val width = progress_bar_bg.width;
            val layoutParams: ViewGroup.LayoutParams = progress_bar.layoutParams
            layoutParams.width = (width * item).toInt()
            progress_bar.layoutParams=layoutParams;
        }
        when (postion) {
            0 -> {
                tv_name.text = "职位要求"
                tv_num.text = "匹配90%"
            }
            1 -> {
                tv_name.text = "沟通进度"
                tv_num.text = "匹配80%"
            }
            2 -> {
                tv_name.text = "期望薪资"
                tv_num.text = "匹配70%"
            }
            3 -> {
                tv_name.text = "工作经验"
                tv_num.text = "匹配60%"
            }
            4 -> {
                tv_name.text = "学历"
                tv_num.text = "匹配50%"
            }
            5 -> {
                tv_name.text = "项目经验"
                tv_num.text = "匹配40%"
            }
            6 -> {
                tv_name.text = "管理经验"
                tv_num.text = "匹配30%"
            }
            7 -> {
                tv_name.text = "全栈能力"
                tv_num.text = "匹配20%"
            }
            8 -> {
                tv_name.text = "英语能力"
                tv_num.text = "匹配10%"
            }
        }

    }
}