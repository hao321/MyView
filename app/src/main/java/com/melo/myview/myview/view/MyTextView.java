package com.melo.myview.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.melo.myview.myview.R;

/**
 * 自定义textview
 * Created by lixiukui on 16/11/23.
 */
public class MyTextView extends View {

    private String TEXT = "这个是textview";
    private Paint mPaint;


    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, R.style.DefaultStyle );
        String name1 = ta.getString(R.styleable.MyTextView_name1);
        String name2 = ta.getString(R.styleable.MyTextView_name2);
        String name3 = ta.getString(R.styleable.MyTextView_name3);
        String name4 = ta.getString(R.styleable.MyTextView_name4);

        TEXT = name1 + "**" + name2 + "**" + name3 + "**" + name4;
        ta.recycle();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.RED);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = getTextRect();
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        int x = (viewWidth - rect.width()) / 2;

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int y = (int) (viewHeight/2 + (fontMetrics.descent - fontMetrics.ascent) /2 - fontMetrics.descent);
        canvas.drawText(TEXT, x, y, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Rect rect = getTextRect();

        int width = measureTextWidth(widthMeasureSpec, rect.width());
        int height = measureTextHeight(heightMeasureSpec, rect.height());
        setMeasuredDimension(width, height);
    }

    private Rect getTextRect(){
        Rect rect = new Rect();
        mPaint.getTextBounds(TEXT, 0, TEXT.length(), rect);
        return rect;
    }

    private int measureTextWidth(int widthMeasureSpec, int textWidth){

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        if(specMode == MeasureSpec.EXACTLY){
            width = specSize;
        }else if(specMode == MeasureSpec.AT_MOST){
            width = textWidth;
        }
        return width;
    }

    private int measureTextHeight(int heightMeasureSpec, int textHeight){

        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        if(specMode == MeasureSpec.EXACTLY){
            height = specSize;
        }else if(specMode == MeasureSpec.AT_MOST){
            height = textHeight;
        }
        return height;
    }
}
