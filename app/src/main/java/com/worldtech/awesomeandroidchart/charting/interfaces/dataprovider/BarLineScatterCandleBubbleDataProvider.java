package com.worldtech.awesomeandroidchart.charting.interfaces.dataprovider;


import com.worldtech.awesomeandroidchart.charting.components.YAxis;
import com.worldtech.awesomeandroidchart.charting.data.BarLineScatterCandleBubbleData;
import com.worldtech.awesomeandroidchart.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
