package com.worldtech.awesomeandroidchart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.worldtech.awesomeandroidchart.adapter.PkAdapter
import com.worldtech.awesomeandroidchart.databinding.LayoutPkBinding


class PkView : FrameLayout {
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

    private val mBinding: LayoutPkBinding
    private val mAdapter = PkAdapter()

    init {
        mBinding = LayoutPkBinding.inflate(LayoutInflater.from(context))
        addView(mBinding.root)
        mBinding.recycleView.adapter = mAdapter

//        mAdapter.setOnItemClickListener(object : OnItemClickListener {
//            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
//                if (mAdapter.select == position) {
//                    return
//                }
//                if (mAdapter.select > -1) {
//                    mAdapter.notifyItemChanged(mAdapter.select)
//                }
//                mAdapter.select = position
//                mAdapter.notifyItemChanged(mAdapter.select)
//            }
//        })
    }

    fun setPlayedData(data: List<Float>) {

        mAdapter.setNewInstance(data.toMutableList())
    }

}



