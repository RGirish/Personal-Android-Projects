package com.example.circle;

import com.example.create.R;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Activity2 extends Activity 
{
	
	public Context cc=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setTitle("Choose the spot!-Image 2");
		getWindow().setWindowAnimations(R.style.Fade);
		cc=this;
		setContentView(new MyView(this));
	}
	
	public class MyView extends View
	{
		float x=0,y=0;
		Paint p=new Paint();
		Intent i;
		int flag=0;
		
		public MyView(Context context) 
		{
			super(context);
			this.setBackgroundResource(R.drawable.image2);
			i=new Intent(cc, Activity3.class);
			p.setColor(Color.BLACK);
			p.setAntiAlias(true);
			p.setAlpha(112);
			p.setStyle(Style.FILL);
		}
		
		@Override
		public void onDraw(Canvas c)
		{
			if(flag==1)
			{
				c.drawCircle(x, y, 15, p);	
				Positions.x2=x;
				Positions.y2=y;
				startActivity(i);
			}
			flag=1;
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
