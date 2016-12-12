package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lixiukui on 16/11/3.
 */
public class SweepGradientView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Matrix mMatrix = new Matrix();
    private float mRotate;
    private Shader mShader;

    public SweepGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        int x = 160;
        int y = 100;

        mShader = new SweepGradient(x, y, new int[]{
            Color.GREEN, Color.RED, Color.BLUE, Color.GREEN},null);
        mPaint.setShader(mShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = mPaint;
        int x = getMeasuredWidth()/2;
        int y = getMeasuredHeight()/2;

//        canvas.translate(300, 300);
        canvas.drawColor(Color.WHITE);

        mMatrix.setRotate(mRotate, x, y);
        mShader.setLocalMatrix(mMatrix);

        mRotate += 3;
        if(mRotate >= 360){
            mRotate = 0;
        }
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(300); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿

        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(60);
        paint1.setAntiAlias(true);

        invalidate();
        canvas.drawCircle(x, y, 320, paint);
        canvas.drawCircle(x, y, 480, paint1);

    }
}
