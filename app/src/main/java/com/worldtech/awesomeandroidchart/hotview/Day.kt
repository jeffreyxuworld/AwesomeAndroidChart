package com.worldtech.awesomeandroidchart.hotview

import java.text.SimpleDateFormat
import java.util.Calendar


/**
 * 封装每天的属性，方便在绘制的时候进行计算
 */
class Day {
    /**
     * 年
     */
    var year = 0

    /**
     * 月
     */
    var month = 0

    /**
     * 日
     */
    var dayOfMonth = 0

    /**
     * 周几
     */
    var dayOfWeek = 0

    /**
     * 贡献次数，默认0
     */
    var contribution = 0

    /**
     * 默认颜色,根据提交次数改变
     */
    var colour = -0x111112

    /**
     * 方格坐标，左上点，右下点，确定矩形范围
     */
    var startX = 0f

    var startY = 0f

    var endX = 0f

    var endY = 0f

    override fun toString(): String {
        //这里直接在弹出框中显示
        return year.toString() + "." + month + "." + dayOfMonth + "/周" + dayOfWeek + "/" + contribution + "次"
    }

    fun getFormatYearMDCn(): String {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month - 1
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        calendar.clear(Calendar.HOUR)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        return sdf.format(calendar.time)
    }
}
