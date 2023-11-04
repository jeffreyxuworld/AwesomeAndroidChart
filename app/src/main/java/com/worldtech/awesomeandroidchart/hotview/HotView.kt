package com.worldtech.awesomeandroidchart.hotview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils
import com.worldtech.awesomeandroidchart.R
import java.util.Calendar
import kotlin.math.abs

/**
 * 仿GitHub的提交活跃表
 * 横屏使用
 */
class HotView @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * 月份
     */
    private val months = StringUtils.getStringArray(R.array.calendar_month)

    /**
     * 默认的padding,绘制的时候不贴边画
     */
    private val paddingHorizontal = SizeUtils.dp2px(3f)
    private val paddingTop = SizeUtils.dp2px(40f)
    private val paddingBottom = SizeUtils.dp2px(3f)

    /**
     * 小方格的默认边长
     */
    private val boxSide = SizeUtils.dp2px(14f)

    /**
     * 小方格间的默认间隔
     */
    private val boxInterval = SizeUtils.dp2px(4f)

    /**
     * 小方格的默认圆角
     */
    private val boxRound = SizeUtils.dp2px(5f)

    /**
     * 所有周的列数
     */
    private var column = 0
    private var mDays: List<Day> = listOf() //一年中所有的天
    private lateinit var boxPaint: Paint  //方格画笔
    private lateinit var todayPaint: Paint  //方格画笔
    private lateinit var monthTextPaint: Paint//文字画笔
    private lateinit var yearTextPaint: Paint//文字画笔
    private lateinit var infoPaint: Paint //弹出框画笔
    private lateinit var startDayPaint: Paint

    private lateinit var metrics: Paint.FontMetrics //测量文字
    private var downX = 0f //按下的点的X坐标
    private var downY = 0f //按下的点的Y坐标
    private var clickDay: Day? = null //按下所对应的天

    init {
        initView()
    }

    private fun initView() {
        //方格画笔
        boxPaint = Paint()
        boxPaint.style = Paint.Style.FILL
        boxPaint.color = DEFAULT_BOX_COLOUR
        boxPaint.isAntiAlias = true
        todayPaint = Paint()
        todayPaint.style = Paint.Style.STROKE
        todayPaint.color = 0xff333333.toInt()
        todayPaint.strokeWidth = SizeUtils.dp2px(1f).toFloat()
        todayPaint.isAntiAlias = true
        //文字画笔
        monthTextPaint = Paint()
        monthTextPaint.style = Paint.Style.FILL
        monthTextPaint.color = ColorUtils.getColor(R.color.color_333333)
        monthTextPaint.textSize = SizeUtils.dp2px(9f).toFloat()
        monthTextPaint.isAntiAlias = true
        yearTextPaint = Paint()
        yearTextPaint.style = Paint.Style.FILL
        yearTextPaint.color = ColorUtils.getColor(R.color.color_333333)
        yearTextPaint.textSize = SizeUtils.dp2px(13f).toFloat()
        yearTextPaint.isAntiAlias = true
        yearTextPaint.isFakeBoldText = true

        //开启档案时间文字
        startDayPaint = Paint()
        startDayPaint.style = Paint.Style.FILL
        startDayPaint.color = 0xff333333.toInt()
        startDayPaint.textSize = SizeUtils.dp2px(9f).toFloat()
        startDayPaint.isAntiAlias = true

        //弹出的方格信息画笔
        infoPaint = Paint()
        infoPaint.style = Paint.Style.FILL
        infoPaint.color = 0xcc888888.toInt()
        infoPaint.textSize = SizeUtils.dp2px(9f).toFloat()
        infoPaint.isAntiAlias = true

        metrics = monthTextPaint.fontMetrics
    }

    fun setDays(startDay: Calendar) {
        mDays = DateFactory.getDays(startDay)
        val width = paddingHorizontal * 2 + (mDays.size / 7 + 2) * (boxSide + boxInterval) + SizeUtils.dp2px(90f)
        val height = paddingTop + paddingBottom + 7 * (boxSide + boxInterval)
        layoutParams = LinearLayout.LayoutParams(width, height)
        invalidate()
    }

    /**
     * 设置某天的次数
     *
     * @param year         年
     * @param month        月
     * @param day          日
     * @param contribution 次数
     */
    fun setData(year: Int, month: Int, day: Int, contribution: Int) {
        //先找到是第几天，为了方便不做参数检测了
        for (d in mDays) {
            if (d.year == year && d.month == month && d.dayOfMonth == day) {
                d.contribution = contribution
                d.colour = getColour(contribution)
                break
            }
        }
        refreshView()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mDays.isEmpty()) {
            return
        }
        column = 0
        canvas.save()
        drawBox(canvas)
        drawPopupInfo(canvas)
        canvas.restore()
    }

    /**
     * 画出1-12月方格小块和上面的月份
     *
     * @param canvas 画布
     */
    private fun drawBox(canvas: Canvas) {
        //方格的左上右下坐标
        var startX: Float
        var startY: Float
        var endX: Float
        var endY: Float
        //起始月份为1月
        var month = mDays[0].month
        for (i in mDays.indices) {
            val day = mDays[i]
            if (i == 0) {
                //第一个月文字
                canvas.drawText(
                    day.year.toString() + "年",
                    paddingHorizontal.toFloat(),
                    (paddingTop * 1 / 4f),
                    yearTextPaint
                )
            }
            if (day.dayOfWeek == 7 && i != 0) {
                //如果当天是周1，那么说明增加了一列
                column++
                //如果列首的月份有变化，那么说明需要画月份
                if (day.month != month) {
                    month = day.month
                    //月份文本的坐标计算,x坐标在变化，而y坐标都是一样的，boxSide/2(间隙)
                    canvas.drawText(
                        months[month - 1],
                        (paddingHorizontal + (column - 1) * (boxSide + boxInterval)).toFloat(),
                        (paddingTop * 3 / 4f),
                        monthTextPaint
                    )

                    if (month == 12) {
                        canvas.drawText(
                            day.year.toString() + "年",
                            (paddingHorizontal + (column - 1) * (boxSide + boxInterval)).toFloat(),
                            (paddingTop * 1 / 4f),
                            yearTextPaint
                        )
                    }
                }
            }
            //计算方格坐标点,x坐标一致随列数的增多而增加,y坐标随行数的增多而变化
            startX = (paddingHorizontal + column * (boxSide + boxInterval)).toFloat()
            startY = (paddingTop + (day.dayOfWeek - 1) * (boxSide + boxInterval)).toFloat()
            endX = startX + boxSide
            endY = startY + boxSide
            //将该方格的坐标保存下来,这样可以在点击方格的时候计算弹框的坐标
            day.startX = startX
            day.startY = startY
            day.endX = endX
            day.endY = endY
            //给画笔设置当前天的颜色
            boxPaint.color = day.colour
            if (i == 0) {
                //今天额外处理
                canvas.drawRoundRect(
                    RectF(startX, startY, endX, endY),
                    boxRound.toFloat(),
                    boxRound.toFloat(),
                    todayPaint
                )
            }
            canvas.drawRoundRect(
                RectF(startX, startY, endX, endY), boxRound.toFloat(), boxRound.toFloat(), boxPaint
            )
        }

//        val startDayBitmap = ImageUtils.getBitmap(R.mipmap.mark_start_day)
//        val startDay = mDays.last()
//        canvas.drawBitmap(
//            startDayBitmap, startDay.startX, startDay.startY - SizeUtils.dp2px(6f), boxPaint
//        )
//        startDayPaint.color = 0xff333333.toInt()
//        canvas.drawText("测试开始标记",
//            startDay.endX + SizeUtils.dp2px(6f),
//            startDay.startY + SizeUtils.dp2px(5f),
//            startDayPaint
//        )
//        startDayPaint.color = 0xff999999.toInt()
//        canvas.drawText(
//            startDay.getFormatYearMDCn(),
//            startDay.endX + SizeUtils.dp2px(6f),
//            startDay.startY + SizeUtils.dp2px(16f),
//            startDayPaint
//        )
        boxPaint.color = DEFAULT_BOX_COLOUR //恢复默认颜色
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //获取点击时候的坐标，用来判断点在哪天，并弹出·
//        if (BuildConfig.DEBUG && MotionEvent.ACTION_DOWN == event.action) {
//            downX = event.x
//            downY = event.y
//            findClickBox()
//        }
        if (MotionEvent.ACTION_DOWN == event.action) {
            downX = event.x
            downY = event.y
//            findClickBox()
        }
        return super.onTouchEvent(event)
    }

    /**
     * 判断是否点击在方格内
     */
    private fun findClickBox() {
        for (day in mDays) {
            //检测点击的坐标如果在方格内，则弹出信息提示
            if (downX >= day.startX && downX <= day.endX && downY >= day.startY && downY <= day.endY) {
                clickDay = day //纪录点击的哪天
                break
            }
        }
        //点击完要刷新，这样每次点击不同的方格，弹窗就可以在相应的位置显示
        refreshView()
    }

    /**
     * 点击弹出文字提示
     */
    private fun refreshView() {
        invalidate()
    }

    /**
     * 画方格上的文字弹框
     *
     * @param canvas 画布
     */
    private fun drawPopupInfo(canvas: Canvas) {
        if (clickDay != null) {
            //先根据方格来画出一个小三角形，坐标就是方格的中间
            val infoPath = Path()
            //先从方格中心
            infoPath.moveTo(clickDay!!.startX + boxSide / 2, clickDay!!.startY + boxSide / 2)
            //然后是方格的左上点
            infoPath.lineTo(clickDay!!.startX, clickDay!!.startY)
            //然后是方格的右上点
            infoPath.lineTo(clickDay!!.endX, clickDay!!.startY)
            //画出三角
            canvas.drawPath(infoPath, infoPaint)
            //画三角上的圆角矩形
            monthTextPaint.color = Color.WHITE
            //得到当天的文本信息
            val popupInfo = clickDay.toString()
            println(popupInfo)
            //计算文本的高度和长度用以确定矩形的大小
            val infoHeight = metrics.descent - metrics.ascent
            val infoLength = monthTextPaint.measureText(popupInfo)
            //            Log.e("height", infoHeight + "");
//            Log.e("length", infoLength + "");
            //矩形左上点应该是x=当前天的x+边长/2-（文本长度/2+文本和框的间隙）
            val leftX = clickDay!!.startX + boxSide / 2 - (infoLength / 2 + boxSide)
            //矩形左上点应该是y=当前天的y+边长/2-（文本高度+上下文本和框的间隙）
            val topY = clickDay!!.startY - (infoHeight + 2 * boxSide)
            //矩形的右下点应该是x=leftX+文本长度+文字两边和矩形的间距
            val rightX = leftX + infoLength + 2 * boxSide
            //矩形的右下点应该是y=当前天的y
            val bottomY = clickDay!!.startY
            //            System.out.println("" + leftX + "/" + topY + "/" + rightX + "/" + bottomY);
            val rectF = RectF(leftX, topY, rightX, bottomY)
            canvas.drawRoundRect(rectF, 8f, 8f, infoPaint)
            //绘制文字,x=leftX+文字和矩形间距,y=topY+文字和矩形上面间距+文字顶到基线高度
            canvas.drawText(
                popupInfo,
                leftX + boxSide,
                (topY + boxSide + abs(metrics.ascent.toDouble())).toFloat(),
                monthTextPaint
            )
            clickDay = null //重新置空，保证点击方格外信息消失
            monthTextPaint.color = Color.GRAY //恢复画笔颜色
        }
    }

    /**
     * 根据提交次数来获取颜色值
     *
     * @param contribution 提交的次数
     * @return 颜色值
     */
    private fun getColour(contribution: Int): Int {
        var colour = 0
        if (contribution <= 0) {
            colour = COLOUR_LEVEL[3]
        }
        if (contribution == 1) {
            colour = COLOUR_LEVEL[2]
        }
        if (contribution == 2) {
            colour = COLOUR_LEVEL[2]
        }
        if (contribution == 3) {
            colour = COLOUR_LEVEL[1]
        }
        if (contribution >= 4) {
            colour = COLOUR_LEVEL[0]
        }
        return colour
    }

    companion object {
        /**
         * 灰色方格的默认颜色
         */
        const val DEFAULT_BOX_COLOUR = 0xfff6f6f6.toInt()

        /**
         * 提交次数颜色值
         */
        val COLOUR_LEVEL = intArrayOf(
            0xff6baf00.toInt(), 0xffabf04b.toInt(), 0xb3d3f891.toInt(), DEFAULT_BOX_COLOUR
        )
    }

}
