package com.szittom.picturtool.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 边缘特殊图像的绘制 锯齿
 * http://blog.csdn.net/yissan
 * <p/>
 * Created by SZITTom on 2016/5/20.
 */
public class CouponDisplayView extends LinearLayout {

    private Paint mPaint;

    //圆间距
    private float gap = 8;
    //半径
    private float radius = 10;

    //圆数量
    private int circleNum;
    private float remain;

    public CouponDisplayView(Context context, AttributeSet attrs, Paint mPaint) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain == 0) {
            remain = (int) (w - gap) % (2 * radius + gap);
        }
        circleNum = (int) ((w - gap) / (2 * radius + gap));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            float x = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            canvas.drawCircle(x, 0, radius, mPaint);
            canvas.drawCircle(x, getHeight(), radius, mPaint);
        }
    }
}
