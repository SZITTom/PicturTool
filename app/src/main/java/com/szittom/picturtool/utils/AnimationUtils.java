package com.szittom.picturtool.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by SZITTom on 2016/5/13.
 */
public class AnimationUtils {

    /**
     * 渐隐动画
     * @return
     */
    public static AlphaAnimation getFadeAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        return alphaAnimation;
    }


    public static ObjectAnimator getUpAnimation(View view, int hight) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "bottomMargin", - hight, 0);
        objectAnimator.setDuration(500);
        return objectAnimator;
    }

    public static ObjectAnimator getDownAnimation(View view, int hight) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "bottomMargin", 0, - hight);
        objectAnimator.setDuration(500);
        return objectAnimator;
    }
}
