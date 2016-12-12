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
public class FiveChessView extends View {

    private Paint mPaint;
    private static final int CHESSSIZE = 120;
    private int mWidth;
    private int mHeight;

    public FiveChessView(Context context, AttributeSet attrs) {
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

        drawChessBoard(canvas);
        drawChess(canvas);
    }

    private void drawChessBoard(Canvas canvas){
        int row = mHeight / CHESSSIZE;
        int col = mWidth / CHESSSIZE;

        for(int i = 0; i < row; i++){
           canvas.drawLine(0,i * CHESSSIZE, mWidth, i * CHESSSIZE, mPaint);
        }
        for(int j = 0; j < col; j++){
            canvas.drawLine(j * CHESSSIZE, 0, j * CHESSSIZE, mHeight, mPaint);
        }
    }

   private void drawChess(Canvas canvas){
       int row = mHeight / CHESSSIZE;
       int col = mWidth / CHESSSIZE;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(i == 3 && j == 4){
                    int centerX = i * CHESSSIZE ;
                    int centerY = j * CHESSSIZE ;
                    Rect rect = new Rect(i * CHESSSIZE - CHESSSIZE/2, j * CHESSSIZE - CHESSSIZE/2, (i + 1)* CHESSSIZE - CHESSSIZE/2, (j + 1) * CHESSSIZE - CHESSSIZE/2);
                    RadialGradient rg = new RadialGradient(centerX,centerY, CHESSSIZE/2 , Color.BLACK, Color.GRAY, Shader.TileMode.CLAMP);
                    mPaint.setShader(rg);
                    canvas.drawOval(new RectF(rect), mPaint);
                }

            }
        }

    }
}
