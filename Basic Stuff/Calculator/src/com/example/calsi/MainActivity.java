package com.example.calsi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	
	public int change=0;
	public char c=0;
	public long placevalue=1;
	public float result=0,temp1=0,temp2=0,operand1=0,operand2=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onclick(View view)
	{
		TextView text = (TextView) findViewById(R.id.textView1);
		int i=0;
		
		switch (view.getId()) 
		{
	     
		case R.id.button1:i=1;break;
			
		case R.id.button2:i=2;break;
			
		case R.id.button3:i=3;break;
			
		case R.id.button4:i=4;break;
			
		case R.id.button5:i=5;break;
			
		case R.id.button6:i=6;break;
			
		case R.id.button7:i=7;break;
			
		case R.id.button8:i=8;break;
			
		case R.id.button9:i=9;break;
			
		case R.id.button10:i=0;	break;
		}
		
		if(change==0)
		{
			temp1=(temp1*placevalue)+i;
			if(temp1<10&&temp1!=0){placevalue=placevalue*10;}
			text.setText(String.valueOf((int)temp1));
		}
		else if(change==1)
		{
		    temp2=(temp2*placevalue)+i;
		    if(temp2<10&&temp2!=0){placevalue=placevalue*10;}
		    text.setText(String.valueOf((int)temp2));
		}
	}

	public void onclickoperator(View view)
	{
		
		if(c==0)
		{
			switch (view.getId()) 
			{
				case R.id.button11:c='p';break;
			
				case R.id.button14:c='m';break;
			
				case R.id.button12:c='i';break;
			
				case R.id.button15:c='d';break;
			}
			change=1;
			placevalue=1;
			operand1=temp1;
		}
		else
		{
			TextView text = (TextView) findViewById(R.id.textView1);
		    operand2=temp2;
		    if(c=='p')
		    {
		    	result=operand1+operand2;
		    	text.setText(String.valueOf((int)result));
		    }
		    else if(c=='m')
		    {
		    	result=operand1-operand2;
		    	text.setText(String.valueOf((int)result));
		    }
		    else if(c=='i')
		    {
		    	result=operand1*operand2;
		    	text.setText(String.valueOf((int)result));
		    }
		    else if(c=='d')
		    {
		    	result=operand1/operand2;
		    	text.setText(String.valueOf((float)result));
		    }
		    switch (view.getId()) 
			{
				case R.id.button11:c='p';break;
			
				case R.id.button14:c='m';break;
				
				case R.id.button12:c='i';break;
				
				case R.id.button15:c='d';break;
			}
			change=1;
			placevalue=1;
		    operand1=result;
		    temp2=operand2=0;
		    result=0;
		}
	}
	
	public void onclickequal(View view)
	{
		TextView text = (TextView) findViewById(R.id.textView1);
	    operand2=temp2;
	    if(c=='p')
	    {
	    	result=operand1+operand2;
	    	text.setText(String.valueOf((int)result));
	    }
	    else if(c=='m')
	    {
	    	result=operand1-operand2;
	    	text.setText(String.valueOf((int)result));
	    }
	    else if(c=='i')
	    {
	    	result=operand1*operand2;
	    	text.setText(String.valueOf((int)result));
	    }
	    else if(c=='d')
	    {
	    	result=operand1/operand2;
	    	text.setText(String.valueOf((float)result));
	    }
	    temp1=temp2=result=0;
		operand1=operand2=0;
		c=0;
		placevalue=1;
		change=0;
	}
	
	public void onclickce(View view)
	{
		TextView text = (TextView) findViewById(R.id.textView1);
		temp1=temp2=result=0;
		operand1=operand2=0;
		c=0;
		placevalue=1;
		change=0;
		text.setText(String.valueOf((int)result));
	}
	
	public void onclickc(View view)
	{
		TextView text = (TextView) findViewById(R.id.textView1);
		if((c=='p'||c=='m'||c=='i'||c=='d')&&(temp2!=0))
		{
			operand2=0;
			temp2=0;
			result=0;
			placevalue=1;
			text.setText(String.valueOf((int)result));
		}
		else
		{
			temp1=temp2=result=0;
			operand1=operand2=0;
			c=0;
			placevalue=1;
			change=0;
			text.setText(String.valueOf((int)result));
		}
	}


}
