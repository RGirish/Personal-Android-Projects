package com.example.circle;

import com.example.create.R;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
		setTitle("Choose the spot!-Image 3");
		getWindow().setWindowAnimations(R.style.Fade);
		setContentView(new MyView(this));
	}
	
	public class MyView extends View
	{
		float x=0,y=0;
		Paint p=new Paint();
		public SQLiteDatabase db;
		
		public MyView(Context context) 
		{
			super(context);
			this.setBackgroundResource(R.drawable.image3);

			db=openOrCreateDatabase("Points.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
			
			try
			{
				db.execSQL("CREATE TABLE the_points(x1 TEXT,y1 TEXT,x2 TEXT,y2 TEXT,x3 TEXT,y3 TEXT);");
			}
			catch(Exception e){}
			
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
    			Positions.x3=x;
    			Positions.y3=y;
    			
    			db.execSQL("INSERT INTO the_points VALUES("+Float.toString(Positions.x1)+","+Float.toString(Positions.y1)+","+Float.toString(Positions.x2)+","+Float.toString(Positions.y2)+","+Float.toString(Positions.x3)+","+Float.toString(Positions.y3)+");");
    			
    			invalidate();
    		}
    		return true;
    		
    	}
		
	}

}

