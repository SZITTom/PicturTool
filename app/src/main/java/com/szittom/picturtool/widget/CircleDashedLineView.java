package com.szittom.picturtool.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * AirFragment 圆—虚线—圆
 *
 * Created by SZITTom on 2016/5/20.
 */
public class CircleDashedLineView extends View{

    //半径
    private float radius = 20;

    private int width;
    private int height;

    private Paint mCirclePaint;
    private Paint mDasheLinePaint;
    private Paint mDasheLinePaintTwice;

    public CircleDashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        setBackgroundColor(Color.GREEN);
        setLayerType( LAYER_TYPE_SOFTWARE , null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        setMeasuredDimension(width, height);
    }

    private void initPaint() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.FILL);
//        mCirclePaint.setShadowLayer(10,-8,-8,Color.parseColor("#88CEDA"));//画阴影

        mDasheLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDasheLinePaint.setColor(Color.WHITE);
        mDasheLinePaint.setStyle(Paint.Style.STROKE);

        mDasheLinePaintTwice = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDasheLinePaintTwice.setColor(Color.parseColor("#88CEDA"));
        mDasheLinePaintTwice.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDashedLineCircle(canvas);
        drawDashedLineCircleTwice(canvas);
        drawLeftCircle(canvas);
        drawRightCircle(canvas);

    }

    private void drawLeftCircle(Canvas canvas) {
        canvas.drawCircle(0,radius,radius,mCirclePaint);
    }

    private void drawRightCircle(Canvas canvas) {
        canvas.drawCircle(getWidth(),radius,radius,mCirclePaint);
    }

    private void drawDashedLineCircle(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0 + radius, radius);
        path.lineTo(width - radius, radius);
        PathEffect effects = new DashPathEffect(new float[] { 8, 8, 8, 8 }, 4);
        mDasheLinePaint.setPathEffect(effects);
        canvas.drawPath(path, mDasheLinePaint);


    }

    private void drawDashedLineCircleTwice(Canvas canvas) {
        Path paths = new Path();
        paths.moveTo(0 + radius, radius - 1);
        paths.lineTo(width - radius, radius - 1);
        PathEffect effectss = new DashPathEffect(new float[] { 8, 8, 8, 8 }, 2);
        mDasheLinePaintTwice.setPathEffect(effectss);
        canvas.drawPath(paths,mDasheLinePaintTwice);
    }
}
