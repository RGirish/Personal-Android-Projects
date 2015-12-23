
package com.example.bluetoothtry1;

import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class FingerPaint extends GraphicsActivity 
{
	
	private Paint mPaint;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
        
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFF0000FF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);

    }
    
    public class MyView extends View {

        private Bitmap  mBitmap;
        private Canvas  mCanvas;
        private Path    mPath;
        private Paint   mBitmapPaint;

        public MyView(Context c) {
            super(c);
            
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) 
        {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) 
        {
            canvas.drawColor(0xFFFFFFFF);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }
        
        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 1;

        private void touch_start(float x, float y) 
        {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }
        private void touch_move(float x, float y) 
        {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;
            }
        }
        private void touch_up() 
        {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) 
            {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
        
        
        
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 10, 0, "Red");
        menu.add(0, 20, 0, "Blue");
        menu.add(0, 30, 0, "Green");
        menu.add(0, 40, 0, "Black");
        menu.add(0, 50, 0, "Grey");
        menu.add(0, 60, 0, "Yellow");
        menu.add(0, 70, 0, "Eraser");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId())
        {
            case 10:
            	mPaint.setStrokeWidth(5);
            	mPaint.setColor(0xffff0000);
            	break;
            case 20:
            	mPaint.setStrokeWidth(5);
            	mPaint.setColor(0xff0000ff);
                break;
            case 30:
            	mPaint.setStrokeWidth(5);
            	mPaint.setColor(0xff00ff00);
                break;
            case 40:
            	mPaint.setStrokeWidth(5);
            	mPaint.setColor(0xff000000);
                break;
            case 50:
            	mPaint.setStrokeWidth(5);
            	mPaint.setColor(0xff777777);
                break;
            case 60:
            	mPaint.setStrokeWidth(5);
            	mPaint.setColor(0xffffff00);
                break;
            case 70:
            	mPaint.setStrokeWidth(10);
            	mPaint.setColor(0xffffffff);
                break;
        }
        
        return true;
    }
}

