package com.szittom.picturtool.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by SZITTom on 2016/5/11.
 *
 * 可以围绕y轴旋转并同时沿着z轴平移从而实现类似3D的效果
 */
public class Rotate3dAnimation extends Animation {

    private final float mFromDeggrees;
    private final float mToDegress;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;

    public Rotate3dAnimation(float mFromDeggrees, float mToDegress, float mCenterX, float mCenterY, float mDepthZ, boolean reverse) {
        this.mFromDeggrees = mFromDeggrees;
        this.mToDegress = mToDegress;
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mDepthZ = mDepthZ;
        this.mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDeggrees = mFromDeggrees;
        float degress = fromDeggrees + ((mToDegress - fromDeggrees) * interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;

        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();
        if (mReverse){
            camera.translate(0.0f,0.0f, mDepthZ * interpolatedTime);
        }else {
            camera.translate(0.0f,0.0f, mDepthZ * (1.0f - interpolatedTime));
        }

        camera.rotateY(degress);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
