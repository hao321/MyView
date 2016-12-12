package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 *阴影view
 * Created by lixiukui on 16/11/1.
 */
public class ShaderView extends View {

    private Paint mPaint;
    private Canvas mCanvas;

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(100);
        this.setLayerType(LAYER_TYPE_HARDWARE, mPaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //
        mPaint.setShadowLayer(10,1,1,Color.RED);
        canvas.drawText("这个阴影不错啊", 100, 100, mPaint);
        //
        mPaint.setShadowLayer(20, 0, 0, Color.GREEN);
        canvas.drawText("就是不一样的效果", 100, 400, mPaint);

        //
        canvas.drawCircle(200, 200, 50, mPaint);
    }
}
