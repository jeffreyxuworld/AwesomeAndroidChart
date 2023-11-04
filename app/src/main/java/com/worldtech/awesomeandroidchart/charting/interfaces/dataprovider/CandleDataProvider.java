package com.worldtech.awesomeandroidchart.charting.interfaces.dataprovider;


import com.worldtech.awesomeandroidchart.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
