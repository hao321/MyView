package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lixiukui on 16/6/30.
 */
public class RectView extends View{

    private float preX;
    private float preY;
    private Paint mPaint;
    private Rect mRect;
    private Path mPath;

    private Bitmap mBitmapBuffer;
    private Canvas mCanvas;

    public RectView(Context context) {
        this(context, null);
    }

    public RectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0  );
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("MYView", "onSizeChanged");
        if(mBitmapBuffer == null){
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            mBitmapBuffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("MYView", "onDraw");
        canvas.drawBitmap(mBitmapBuffer, 0, 0, null);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.reset();
                if(preX > x && preY >y){
                    mPath.addRect(x, y, preX, preY, Path.Direction.CCW);
                }else {
                    mPath.addRect(preX, preY, x, y, Path.Direction.CCW);
                }

                this.invalidate();

                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);
                this.invalidate();
                break;
            default:
                break;

        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("MYView", "onMeasure");
        MeasureSpec.makeMeasureSpec(200, MeasureSpec.AT_MOST);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.d("MYView", "specMode = " + specMode + " specSize = " + specSize);
//        setMeasuredDimension(200, 200);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("MYView", "onLayout");
    }
}
