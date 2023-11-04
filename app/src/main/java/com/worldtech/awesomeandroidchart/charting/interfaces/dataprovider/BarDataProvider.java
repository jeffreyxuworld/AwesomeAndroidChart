package com.worldtech.awesomeandroidchart.charting.interfaces.dataprovider;


import com.worldtech.awesomeandroidchart.charting.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isHighlightFullBarEnabled();
}
