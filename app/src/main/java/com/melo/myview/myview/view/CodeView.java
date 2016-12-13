package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * 验证码
 * Created by lixiukui on 2016/12/12.
 */
public class CodeView extends View {

    private Paint mPaint;

    private int mWidth;
    private int mHeight;
    private String mCode;
    private int count = 4;
    private Random random = new Random();
    private Rect codeRect;
    private int line_count = 50;
    /**
     * 类中new的时候调用的
     * @param context
     */
    public CodeView(Context context) {
        this(context, null);
    }

    /**
     * xml中声明view的时候调用的
     * @param context
     * @param attrs
     */
    public CodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initCode();
        initCodeRect();
    }

    private void initCodeRect() {
        codeRect = getTextRect(mCode);
    }

    private void initCode() {
        String str = "";
        for(int i = 0; i< count; i++){
            int ran = random.nextInt(10);
            str = str + ran;
        }
        mCode = str;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(100);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect borderRect = new Rect(0, 0, mWidth, mHeight);
        borderRect.inset(2, 2);
        canvas.drawRect(borderRect, mPaint);

        mPaint.setStyle(Paint.Style.FILL);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int x = (mWidth - codeRect.width()) / 2;
        int y = (int) (mHeight/2 + (fontMetrics.descent - fontMetrics.ascent) /2 - fontMetrics.descent);
        Log.d("Height", "y = " + y + "height = " + mHeight);
        canvas.drawText(mCode, x, y, mPaint);

        drawLine(canvas);

    }

    private void drawLine(Canvas canvas) {
        for(int i = 0; i < line_count; i++){
            int x1 = random.nextInt(mWidth);
            int y1 = random.nextInt(mHeight);

            int x2 = random.nextInt(mWidth);
            int y2 = random.nextInt(mHeight);

            mPaint.setColor(Color.GRAY);
            canvas.drawLine(x1, y1, x2, y2, mPaint);
        }
    }

    private int measureWidth(int widthMeasureSpec) {

        int widthSize = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            widthSize = specWidth;
        }else if(specMode == MeasureSpec.AT_MOST){
            widthSize = getTextRect(mCode).width() + getPaddingLeft() + getPaddingRight();
        }

        return widthSize;
    }
    private int measureHeight(int heightMeasureSpec) {

        int heightSize = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            heightSize = specHeight;
        }else if(specMode == MeasureSpec.AT_MOST){
            heightSize = getTextRect(mCode).height() + getPaddingBottom() + getPaddingTop();
        }

        return heightSize;
    }

    private Rect getTextRect(String code){
        Rect rect = new Rect();
        mPaint.getTextBounds(code, 0, code.length(), rect);
        return rect;
    }
}
