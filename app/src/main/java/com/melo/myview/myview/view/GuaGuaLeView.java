package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.melo.myview.myview.R;

/**
 * 刮刮乐
 * Created by lixiukui on 16/11/7.
 */
public class GuaGuaLeView extends View {
    private Paint mPaint;
    private Bitmap bgBitmap;
    private Bitmap bufferBitmap ;
    private Canvas bufferCanvas;

    private Paint clearPaint;

    int curX , curY;

    public GuaGuaLeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        clearPaint.setFakeBoldText(true);
        clearPaint.setStrokeWidth(100);

        drawBg();

    }

    /**
     * 在图片上写字，并将其设置成view的背景（）
     */
    private void drawBg(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap tempBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        // 从资源中获取的bitmap 不可以修改， 需要重新复制一份
        bgBitmap = tempBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bgBitmap);
//        this.setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(100);
        canvas.drawText("这个真实b", 100, 100, mPaint);
        this.setBackgroundDrawable(new BitmapDrawable(getResources(), bgBitmap));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
           canvas.drawBitmap(bufferBitmap, 0, 0, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bufferBitmap = Bitmap.createBitmap( w, h,Bitmap.Config.ARGB_8888);
        bufferCanvas = new Canvas(bufferBitmap);
        bufferCanvas.drawColor(Color.parseColor("#FF808080"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                curX = x;
                curY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                bufferCanvas.drawLine(curX, curY, x, y, clearPaint);
                curX = x;
                curY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }
}
