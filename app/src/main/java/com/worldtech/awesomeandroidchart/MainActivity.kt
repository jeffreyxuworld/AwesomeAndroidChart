package com.worldtech.awesomeandroidchart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.worldtech.awesomeandroidchart.databinding.ActivityMainBinding
import com.worldtech.awesomeandroidchart.charting.components.AxisBase
import com.worldtech.awesomeandroidchart.charting.components.Legend
import com.worldtech.awesomeandroidchart.charting.components.XAxis
import com.worldtech.awesomeandroidchart.charting.components.YAxis
import com.worldtech.awesomeandroidchart.charting.data.PieData
import com.worldtech.awesomeandroidchart.charting.data.PieDataSet
import com.worldtech.awesomeandroidchart.charting.data.PieEntry
import com.worldtech.awesomeandroidchart.charting.data.RadarData
import com.worldtech.awesomeandroidchart.charting.data.RadarDataSet
import com.worldtech.awesomeandroidchart.charting.data.RadarEntry
import com.worldtech.awesomeandroidchart.charting.formatter.FilesFormatter
import com.worldtech.awesomeandroidchart.charting.formatter.IAxisValueFormatter
import com.worldtech.awesomeandroidchart.charting.interfaces.datasets.IRadarDataSet
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        initView()

    }

    private fun initView() {
        mBinding?.refreshLayout?.setOnRefreshListener {
            mBinding?.refreshLayout!!.finishRefresh()
        }


        //----------------------------------------------------------------------------------
        //竖向柱形图
        val list = listOf(
            ResumeSaw("10-28", 6),
            ResumeSaw("10-29", 15),
            ResumeSaw("10-30", 30),
            ResumeSaw("10-31", 60),
            ResumeSaw("11-01", 118),
            ResumeSaw("11-02", 218)
        )
        mBinding?.resumeSawBarView?.setPlayedData(list)

        //----------------------------------------------------------------------------------
        //饼状图
        mBinding?.pieChart?.description?.isEnabled = false
        mBinding?.pieChart?.isDrawHoleEnabled = true
        mBinding?.pieChart?.setExtraOffsets(40f, 0f, 40f, 0f)
        mBinding?.pieChart?.setHoleColor(Color.WHITE)
        mBinding?.pieChart?.setTransparentCircleColor(Color.WHITE)
        mBinding?.pieChart?.setTransparentCircleAlpha(0)
        mBinding?.pieChart?.holeRadius = 52f
        mBinding?.pieChart?.transparentCircleRadius = 0f
        mBinding?.pieChart?.setDrawCenterText(false)
        mBinding?.pieChart?.rotationAngle = 200f
        mBinding?.pieChart?.isRotationEnabled = false
        mBinding?.pieChart?.isHighlightPerTapEnabled = false
        mBinding?.pieChart?.setDrawEntryLabels(false)
        val l: Legend? =  mBinding?.pieChart?.legend
        l?.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l?.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l?.orientation = Legend.LegendOrientation.VERTICAL
        l?.setDrawInside(false)
        l?.isEnabled = false

        val colors = ArrayList<Int>()
        colors.add(Color.rgb(26, 230, 230))
        colors.add(Color.rgb(230, 148, 26))
        colors.add(Color.rgb(17, 17, 238))
        val entries = ArrayList<PieEntry>()
        entries.add(
            PieEntry(
                16.7f,
                "面议"
            )
        )
        entries.add(
            PieEntry(
                26f,
                "28-42k"
            )
        )
        entries.add(
            PieEntry(
                45.1f,
                "14-28k"
            )
        )
        val dataSet = PieDataSet(
            entries,
            ""
        )
        dataSet.sliceSpace = 2f
        dataSet.valueLinePart2Length = 0.9f
        dataSet.colors = colors
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val data =
            PieData(dataSet)
        data.setValueFormatter(FilesFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.rgb(51, 51, 51))
        //设置数据
        mBinding?.pieChart?.data = data
        mBinding?.pieChart?.highlightValues(null)
        mBinding?.pieChart?.invalidate()


        //----------------------------------------------------------------------------------
        //圆形雷达图
        mBinding?.radarChart?.setBackgroundColor(Color.WHITE)
        mBinding?.radarChart?.description?.isEnabled = false

        mBinding?.radarChart?.webLineWidth = 0f
        mBinding?.radarChart?.webColor = Color.rgb(237, 237, 237)
        mBinding?.radarChart?.webLineWidthInner = 2f
        mBinding?.radarChart?.webColorInner = Color.rgb(237, 237, 237)
        mBinding?.radarChart?.webAlpha = 100
        mBinding?.radarChart?.isRotationEnabled = false

        setData()
        val xAxis: XAxis? =  mBinding?.radarChart?.getXAxis()
        xAxis?.textSize = 12f
        xAxis?.yOffset = 0f
        xAxis?.xOffset = 0f
        xAxis?.valueFormatter = object :
            IAxisValueFormatter {
        private val mActivities = arrayOf("职位要求", "沟通进度", "期望薪资", "工作经验", "学历", "项目经验", "管理经验", "全栈能力", "英语能力")
            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return mActivities[value.toInt() % mActivities.size]
            }
        }
        xAxis?.textColor = Color.BLACK

        val yAxis: YAxis? =  mBinding?.radarChart?.yAxis

        yAxis?.setLabelCount(6, true)
        yAxis?.textSize = 9f
        yAxis?.axisMinimum = 0f
        yAxis?.axisMaximum = 5f
        yAxis?.setDrawLabels(false)

        val legend: Legend? =  mBinding?.radarChart?.legend
        legend?.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend?.orientation = Legend.LegendOrientation.HORIZONTAL
        legend?.setDrawInside(false)
        legend?.xEntrySpace = 7f
        legend?.yEntrySpace = 5f
        legend?.textColor = Color.RED
        legend?.isEnabled = false


        mBinding?.tvSwitch?.setOnClickListener {
            switchView();
        }
        mBinding?.playFrequentlyView?.setPlayedData(arrayListOf(0.9f,0.8f,0.7f,0.6f,0.5f,0.4f,0.3f,0.2f,0.1f))


        //----------------------------------------------------------------------------------
        //类似于 Github 热力图
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = 2022
        calendar[Calendar.MONTH] = Calendar.DECEMBER
        calendar[Calendar.DAY_OF_MONTH] = 1
        calendar.clear(Calendar.HOUR)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        mBinding?.githubHot?.setDays(calendar)

        //塞入对应日期的贡献次数
        mBinding?.githubHot?.setData(2023, 9, 9, 2)
        mBinding?.githubHot?.setData(2023, 9, 10, 1)
        mBinding?.githubHot?.setData(2023, 10, 1, 10)
        mBinding?.githubHot?.setData(2023, 10, 31, 10)
        mBinding?.githubHot?.setData(2023, 8, 9, 3)
        mBinding?.githubHot?.setData(2023, 4, 20, 2)
        mBinding?.githubHot?.setData(2023, 7, 13, 3)
        mBinding?.githubHot?.setData(2023, 6, 14, 3)
        mBinding?.githubHot?.setData(2023, 2, 15, 4)
    }

    private fun switchView() {
        if (mBinding?.radarChart?.visibility== View.VISIBLE) {
            mBinding?.radarChart?.visibility= View.GONE;
            mBinding?.playFrequentlyView?.visibility= View.VISIBLE;
        }else{
            mBinding?.radarChart?.visibility= View.VISIBLE;
            mBinding?.playFrequentlyView?.visibility= View.GONE;
        }
    }

    private fun setData() {
        val mul = 5f
        val min = 0f
        val cnt = 9
        val entries2 = ArrayList<RadarEntry>()
//        for (i in 0 until cnt) {
//            val val2 = (Math.random() * mul).toFloat() + min
        entries2.add(
            RadarEntry(
                5f
            )
        )
        entries2.add(
            RadarEntry(
                4.5f
            )
        )
        entries2.add(
            RadarEntry(
                4.5f
            )
        )
        entries2.add(
            RadarEntry(
                4.7f
            )
        )
        entries2.add(
            RadarEntry(
                3f
            )
        )
        entries2.add(
            RadarEntry(
                4.8f
            )
        )
        entries2.add(
            RadarEntry(
                4.8f
            )
        )
        entries2.add(
            RadarEntry(
                2f
            )
        )
        entries2.add(
            RadarEntry(
                4.5f
            )
        )
//        }
        val set2 = RadarDataSet(
            entries2,
            ""
        )
        set2.color = Color.rgb(190, 240, 0)
        set2.fillColor = Color.argb(70, 227, 248, 161)
        set2.setDrawFilled(true)
        set2.fillAlpha = 170
        set2.lineWidth = 2f
        set2.isDrawHighlightCircleEnabled = false
        set2.setDrawHighlightIndicators(false)
        val sets = java.util.ArrayList<IRadarDataSet>()
        sets.add(set2)
        val data =
            RadarData(sets)
        //        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f)
        data.setDrawValues(false)
        data.setValueTextColor(Color.RED)
        mBinding?.radarChart?.data = data
        mBinding?.radarChart?.invalidate()
    }



}