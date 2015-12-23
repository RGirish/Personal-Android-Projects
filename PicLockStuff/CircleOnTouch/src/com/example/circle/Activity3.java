package com.example.circle;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Activity3 extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().setWindowAnimations(R.style.Fade);
		setContentView(new MyView(this));
	}
	
	public class MyView extends View
	{
		float x=0,y=0;
		Paint p=new Paint();
		
		public MyView(Context context) 
		{
			super(context);
			this.setBackgroundResource(R.drawable.image3);

			p.setColor(Color.BLACK);
			p.setAntiAlias(true);
			p.setAlpha(112);
			p.setStyle(Style.FILL);
		}
		
		@Override
		public void onDraw(Canvas c)
		{
			c.drawCircle(x, y, 15, p);
		}
		
		@Override
    	public boolean onTouchEvent(MotionEvent mev)
    	{
    		if(mev.getAction()==MotionEvent.ACTION_DOWN)
    		{
    			x=mev.getX();
    			y=mev.getY();
    			invalidate();
    		}
    		return true;
    		
    	}
		
	}


}
