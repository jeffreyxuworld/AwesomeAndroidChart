package com.worldtech.awesomeandroidchart;

import android.graphics.Typeface;

public class FontProvider {

    private static final Typeface NORMAL =
            Typeface.createFromAsset(GlobeContext.INSTANCE.getContext().getAssets(), "fonts/akrobat-regular.ttf");
    private static final Typeface BLACK =
            Typeface.createFromAsset(GlobeContext.INSTANCE.getContext().getAssets(), "fonts/akrobat-black.otf");

    public static Typeface getCustomFontNormal() {
        return NORMAL;
    }

    public static Typeface getCustomFontBlack() {
        return BLACK;
    }

}
