package com.worldtech.awesomeandroidchart.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.worldtech.awesomeandroidchart.charting.utils.Utils;
import com.worldtech.awesomeandroidchart.charting.utils.ViewPortHandler;
import com.worldtech.awesomeandroidchart.charting.interfaces.datasets.IScatterDataSet;

/**
 * Created by wajdic on 15/06/2016.
 * Created at Time 09:08
 */
public class ChevronUpShapeRenderer implements IShapeRenderer
{


    @Override
    public void renderShape(Canvas c, IScatterDataSet dataSet, ViewPortHandler viewPortHandler,
                            float posX, float posY, Paint renderPaint) {

        final float shapeHalf = dataSet.getScatterShapeSize() / 2f;

        renderPaint.setStyle(Paint.Style.STROKE);
        renderPaint.setStrokeWidth(Utils.convertDpToPixel(1f));

        c.drawLine(
                posX,
                posY - (2 * shapeHalf),
                posX + (2 * shapeHalf),
                posY,
                renderPaint);

        c.drawLine(
                posX,
                posY - (2 * shapeHalf),
                posX - (2 * shapeHalf),
                posY,
                renderPaint);

    }
}
