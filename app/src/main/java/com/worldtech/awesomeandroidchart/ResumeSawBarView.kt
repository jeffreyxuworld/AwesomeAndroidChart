package com.worldtech.awesomeandroidchart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.worldtech.awesomeandroidchart.databinding.LayoutVerticalBarViewBinding

class ResumeSawBarView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private val mBinding: LayoutVerticalBarViewBinding
    private val mAdapter = VerticalBarAdapter()

    init {
        mBinding = LayoutVerticalBarViewBinding.inflate(LayoutInflater.from(context))
        addView(mBinding.root)

        mBinding.recycle.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mBinding.recycle.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                if (mAdapter.select == position) {
                    return
                }
                if (mAdapter.select > -1) {
                    mAdapter.notifyItemChanged(mAdapter.select)
                }
                mAdapter.select = position
                mAdapter.notifyItemChanged(mAdapter.select)
            }
        })
    }

    fun setPlayedData(data: List<ResumeSaw>) {
        var max = data.maxWith(compareBy { it.num }).num
        if (max < 100) {
            if (max % 3 != 0) {
                max = (max / 3 + 1) * 3
            }
        } else if (max < 1000) {
            if (max % 30 != 0) {
                max = (max / 30 + 1) * 30
            }
        } else if (max < 10000) {
            if (max % 300 != 0) {
                max = (max / 300 + 1) * 300
            }
        }
        mBinding.max3.text = max.toString()
        mBinding.max2.text = (max * 2 / 3).toString()
        mBinding.max1.text = (max / 3).toString()
        mAdapter.maxNum = max
        mAdapter.setNewInstance(data.toMutableList())
    }

}

data class ResumeSaw(
//    val year: Int,
    val day: String,
    val num: Int,
)

class VerticalBarAdapter : BaseQuickAdapter<ResumeSaw, BaseViewHolder> {
    companion object {
        val MAX_HEIGHT = SizeUtils.dp2px(108f)
    }

    constructor() : super(R.layout.item_vertical_bar)

    var select = -1
    var maxNum = 150

    override fun convert(holder: BaseViewHolder, item: ResumeSaw) {
        val year = holder.getView<TextView>(R.id.year)
        val top_num = holder.getView<TextView>(R.id.top_num)
        val progress_bar = holder.getView<View>(R.id.progress_bar)
        val layoutParams: ViewGroup.LayoutParams = progress_bar.layoutParams
        layoutParams.height = MAX_HEIGHT * item.num / maxNum

//        year.text = item.year.toString()
        year.text = item.day

        if (holder.layoutPosition == select) {
            year.setTextColor(ColorUtils.getColor(R.color.color_f57449))
            progress_bar.setBackgroundResource(R.drawable.bg_color_f57449_corners_12)
            top_num.visibility = View.VISIBLE
            top_num.text = "曝光" + item.num
        } else {
            year.setTextColor(ColorUtils.getColor(R.color.main_text))
            progress_bar.setBackgroundResource(R.drawable.bg_color_1ae6e6_corners_12)
            top_num.visibility = View.GONE

        }

    }

}