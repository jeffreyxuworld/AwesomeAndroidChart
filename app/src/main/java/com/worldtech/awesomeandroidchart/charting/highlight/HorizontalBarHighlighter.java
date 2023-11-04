package com.worldtech.awesomeandroidchart.charting.highlight;

import com.worldtech.awesomeandroidchart.charting.data.BarData;
import com.worldtech.awesomeandroidchart.charting.data.DataSet;
import com.worldtech.awesomeandroidchart.charting.data.Entry;
import com.worldtech.awesomeandroidchart.charting.interfaces.dataprovider.BarDataProvider;
import com.worldtech.awesomeandroidchart.charting.interfaces.datasets.IBarDataSet;
import com.worldtech.awesomeandroidchart.charting.interfaces.datasets.IDataSet;
import com.worldtech.awesomeandroidchart.charting.utils.MPPointD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp Jahoda on 22/07/15.
 */
public class HorizontalBarHighlighter extends BarHighlighter {

	public HorizontalBarHighlighter(BarDataProvider chart) {
		super(chart);
	}

	@Override
	public Highlight getHighlight(float x, float y) {

		BarData barData = mChart.getBarData();

		MPPointD pos = getValsForTouch(y, x);

		Highlight high = getHighlightForX((float) pos.y, y, x);
		if (high == null)
			return null;

		IBarDataSet set = barData.getDataSetByIndex(high.getDataSetIndex());
		if (set.isStacked()) {

			return getStackedHighlight(high,
					set,
					(float) pos.y,
					(float) pos.x);
		}

		MPPointD.recycleInstance(pos);

		return high;
	}

	@Override
	protected List<Highlight> buildHighlights(IDataSet set, int dataSetIndex, float xVal, DataSet.Rounding rounding) {

		ArrayList<Highlight> highlights = new ArrayList<>();

		//noinspection unchecked
		List<Entry> entries = set.getEntriesForXValue(xVal);
		if (entries.size() == 0) {
			// Try to find closest x-value and take all entries for that x-value
			final Entry closest = set.getEntryForXValue(xVal, Float.NaN, rounding);
			if (closest != null)
			{
				//noinspection unchecked
				entries = set.getEntriesForXValue(closest.getX());
			}
		}

		if (entries.size() == 0)
			return highlights;

		for (Entry e : entries) {
			MPPointD pixels = mChart.getTransformer(
					set.getAxisDependency()).getPixelForValues(e.getY(), e.getX());

			highlights.add(new Highlight(
					e.getX(), e.getY(),
					(float) pixels.x, (float) pixels.y,
					dataSetIndex, set.getAxisDependency()));
		}

		return highlights;
	}

	@Override
	protected float getDistance(float x1, float y1, float x2, float y2) {
		return Math.abs(y1 - y2);
	}
}
