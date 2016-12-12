package com.melo.myview.myview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.melo.myview.myview.R;

/**
 * Created by lixiukui on 16/11/29.
 */
public class CustomImageView extends ImageView {
    private Paint mPaint;
    private Xfermode mXfermode;
    private Path mPath;
    private Shader shader;
    private Matrix matrix = new Matrix();
    private float rotate;

    private int border;
    private int borderColor;
    private boolean isCircle;
    private int cornerRadius;
    private Drawable showDrawable;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPath = new Path();

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CustomImageView);
        border = (int) ta.getDimensionPixelSize(R.styleable.CustomImageView_border, 0);
        borderColor = ta.getColor(R.styleable.CustomImageView_border_color, Color.GRAY);
        isCircle = ta.getBoolean(R.styleable.CustomImageView_isCircle, false);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.CustomImageView_corner_radius, 0);
        showDrawable = ta.getDrawable(R.styleable.CustomImageView_show_shape);
        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawble = getDrawable();
        if(drawble == null){
            super.onDraw(canvas);
        }

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int radius = 0;
        RectF oval;
        mPath.reset();
        if(isCircle){
            radius = Math.min(width, height)/2;
            oval = new RectF(0, 0, 2 * radius, 2 * radius);
            mPath.addOval(oval, Path.Direction.CCW);

        }
        else {
            oval = new RectF(0, 0, width, height);
            mPath.addRoundRect(oval, cornerRadius, cornerRadius, Path.Direction.CCW);
        }
        int layerId = canvas.saveLayer(getPaddingLeft(), getPaddingTop(), width,
                height, null, Canvas.ALL_SAVE_FLAG);
        Bitmap bitmap = ((BitmapDrawable)drawble).getBitmap();

        canvas.drawBitmap(bitmap, new Rect(0,0, drawble.getIntrinsicWidth(), drawble.getIntrinsicHeight()), oval, null);

        mPaint.setXfermode(mXfermode);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
//        if(showDrawable != null){
//            Bitmap showBitmap = ((BitmapDrawable)showDrawable).getBitmap();
//            canvas.drawBitmap(showBitmap, 0, 0, mPaint);
//        }else {
//            canvas.drawPath(mPath, mPaint);
//        }
        canvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

        if(border != 0){
            mPaint.setColor(borderColor);
            mPaint.setStrokeWidth(border);
            mPaint.setStyle(Paint.Style.STROKE);
            shader = new SweepGradient(0, 0, new int[]{
                    Color.GREEN, Color.RED, Color.BLUE, Color.GREEN},null);
            matrix.setRotate(rotate, radius, radius);
            shader.setLocalMatrix(matrix);
            rotate += 3;
            if(rotate >= 360){
                rotate = 0;
            }
            mPaint.setShader(shader);
            oval.inset(border/2, border/2);
//            canvas.drawOval(oval, mPaint);
            if(isCircle){
                canvas.drawOval(oval, mPaint);
            }else {
                canvas.drawRoundRect(oval,cornerRadius, cornerRadius, mPaint);
            }
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
