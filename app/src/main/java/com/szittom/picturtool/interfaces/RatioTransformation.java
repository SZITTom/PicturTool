package com.szittom.picturtool.interfaces;

import android.graphics.Bitmap;

import com.jude.beam.Utils;
import com.squareup.picasso.Transformation;
import com.szittom.picturtool.app.App;

/**
 * Created by SZITTom on 2016/4/20.
 */
public class RatioTransformation implements Transformation {

    private final int number;

    public RatioTransformation(int number){
        this.number = number;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = Utils.getScreenWidth(App.getInstance().getApplicationContext())/number;

        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        if (result != source) {
            // Same bitmap is returned if sizes are the same
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }
}
