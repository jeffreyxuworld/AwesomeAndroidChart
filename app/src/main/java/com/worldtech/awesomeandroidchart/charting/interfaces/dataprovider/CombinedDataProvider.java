package com.worldtech.awesomeandroidchart.charting.interfaces.dataprovider;


import com.worldtech.awesomeandroidchart.charting.data.CombinedData;

/**
 * Created by philipp on 11/06/16.
 */
public interface CombinedDataProvider extends LineDataProvider, BarDataProvider, BubbleDataProvider, CandleDataProvider, ScatterDataProvider {

    CombinedData getCombinedData();
}
