package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lixiukui on 16/11/2.
 */
public class RadiolGradientView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public RadiolGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect(0, 0, mWidth, mHeight);
        int r = mWidth > mHeight ? mHeight /2 : mWidth/ 2;
        RadialGradient rg = new RadialGradient(mWidth/2, mHeight/2, r, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        mPaint.setShader(rg);

//        canvas.drawRect(rect, mPaint);
        canvas.drawOval(new RectF(rect), mPaint);



    }
}
