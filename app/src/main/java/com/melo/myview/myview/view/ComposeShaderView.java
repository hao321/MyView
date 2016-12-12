package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.*;
import android.graphics.BitmapShader;
import android.util.AttributeSet;
import android.view.View;

import com.melo.myview.myview.R;

/**
 * Created by lixiukui on 16/11/3.
 */
public class ComposeShaderView extends View {

    private Paint mPaint;
    public ComposeShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        android.graphics.BitmapShader bs = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);

        LinearGradient lg = new LinearGradient(0, 0, getMeasuredWidth(), 0, Color.BLUE, Color.RED, Shader.TileMode.CLAMP);
        ComposeShader cs = new ComposeShader(bs, lg, PorterDuff.Mode.ADD);
        mPaint.setShader(cs);
        canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), mPaint);
    }
}
