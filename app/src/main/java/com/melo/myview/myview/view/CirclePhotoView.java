package com.melo.myview.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.melo.myview.myview.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by lixiukui on 16/11/3.
 */
public class CirclePhotoView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Canvas srcCanvas;
    private Bitmap headBitmap ;
    private Bitmap srcBitmap;
    private int minWidth;

    public CirclePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
       headBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        minWidth = Math.min(headBitmap.getWidth(), headBitmap.getHeight());

        srcBitmap = Bitmap.createBitmap(minWidth, minWidth, Bitmap.Config.ARGB_8888);
        srcCanvas = new Canvas(srcBitmap);
        int r = minWidth / 2;
        srcCanvas.drawCircle(r, r, r, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width ;
        int specModeW = MeasureSpec.getMode(widthMeasureSpec);
        int sepcWidth = MeasureSpec.getSize(widthMeasureSpec);
        if(specModeW == MeasureSpec.EXACTLY){
            width = sepcWidth;
        }else {
            Log.d("HHH", "minWidth = " + minWidth);
            width = Math.min(sepcWidth, minWidth);
        }

        int height;
        int specModeH = MeasureSpec.getMode(heightMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(specModeH == MeasureSpec.EXACTLY){
            height = specHeight;
        }else {
            height = Math.min(specHeight, minWidth);
        }
        Log.d("HHH", "width = " + width);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = srcBitmap.getWidth();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmap = sacleImg(bitmap);
        int layer = canvas.saveLayer(0, 0, bitmap.getWidth(), bitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(headBitmap,0, 0, null);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        bitmap = sacleImg(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

        canvas.restoreToCount(layer);
    }

    private Bitmap scaleImage(Bitmap bitmap) {
        float scaleX = getMeasuredWidth() / bitmap.getWidth();
        float scaleY = getMeasuredHeight() / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    //这个方式是不能放大图片的，只能缩小
    private Bitmap sacleImg(Bitmap bitmap){
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int scale = 1;
        if(w > h){
            scale =  imgHeight / h;
        }else {
            scale =  imgWidth /w;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = 1/5;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        return bitmap;
    }
}

