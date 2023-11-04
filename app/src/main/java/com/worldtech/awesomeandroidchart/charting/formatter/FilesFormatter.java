
package com.worldtech.awesomeandroidchart.charting.formatter;

import com.worldtech.awesomeandroidchart.charting.components.AxisBase;
import com.worldtech.awesomeandroidchart.charting.data.Entry;
import com.worldtech.awesomeandroidchart.charting.data.PieEntry;
import com.worldtech.awesomeandroidchart.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * This IValueFormatter is just for convenience and simply puts a "%" sign after
 * each value. (Recommeded for PieChart)
 *
 * @author Philipp Jahoda
 */
public class FilesFormatter implements IValueFormatter, IAxisValueFormatter
{

//    protected DecimalFormat mFormat;

    public FilesFormatter() {
//        mFormat = new DecimalFormat("###,###,##0.0");
    }

    /**
     * Allow a custom decimalformat
     *
     * @param format
     */
    public FilesFormatter(DecimalFormat format) {
//        this.mFormat = format;
    }

    // IValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if (entry instanceof PieEntry){
            PieEntry temp= (PieEntry) entry;
//            Log.e("TAGTAG",value+"---"+(int)value);
            if ((value==Integer.MAX_VALUE)){
                return temp.getLabel()+"(0)";
            }else {
                return temp.getLabel()+"("+(int)value+")";
            }
        }
        return (int)value+"";
    }

    // IAxisValueFormatter
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return value + "";
    }

    public int getDecimalDigits() {
        return 1;
    }
}
