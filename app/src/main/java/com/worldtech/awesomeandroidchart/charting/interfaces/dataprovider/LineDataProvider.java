package com.worldtech.awesomeandroidchart.charting.interfaces.dataprovider;


import com.worldtech.awesomeandroidchart.charting.components.YAxis;
import com.worldtech.awesomeandroidchart.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
