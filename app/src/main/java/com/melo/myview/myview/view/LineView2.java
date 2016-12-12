package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lixiukui on 16/6/29.
 */
public class LineView2 extends View {

    private int preX;
    private int preY;
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmapBuffer;
    private Canvas mBitmapCanvas;

    public LineView2(Context context) {
        this(context, null);
    }

    public LineView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mBitmapBuffer == null){
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            mBitmapBuffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mBitmapCanvas = new Canvas(mBitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapBuffer, 0, 0, null);
        //canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                preX = x;
                preY = y;
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:

                mPath.quadTo(preX, preY, x, y);
                mBitmapCanvas.drawPath(mPath, mPaint);
                this.invalidate();
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:

                mBitmapCanvas.drawPath(mPath, mPaint);
                this.invalidate();
                break;
        }
        return true;
    }
}
