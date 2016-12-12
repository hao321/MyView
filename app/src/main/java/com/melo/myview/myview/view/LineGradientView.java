package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * 线性渐变的view
 * Created by lixiukui on 16/11/1.
 */
public class LineGradientView extends View {

    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;

    private int orientation = 0;

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public LineGradientView(Context context, AttributeSet attrs) {
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
        LinearGradient lg = null;
        if(orientation == VERTICAL){
            lg = new LinearGradient(rect.right/2, rect.top, rect.right/2, rect.bottom, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        }else if(orientation == HORIZONTAL){
            lg = new LinearGradient(rect.left, rect.bottom/2, rect.right, rect.bottom/2, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        }

        mPaint.setShader(lg);
        canvas.drawRect(rect, mPaint);
    }

    private void setShaderOrientation(int orientation){
        this.orientation = orientation;
    }

    private void refreshView(){
        this.invalidate();
    }
}
