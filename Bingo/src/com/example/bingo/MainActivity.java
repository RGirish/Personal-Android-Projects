package com.example.bingo;

import java.util.ArrayList;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Color;

public class MainActivity extends Activity 
{

	int count=0;
	boolean gameover=false;	
	Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button b10;
    Button b11;
    Button b12;
    Button b13;
    Button b14;
    Button b15;
    Button b16;    
    Button random;
    TextView TV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		b1=(Button)findViewById(R.id.b441);
	    b2=(Button)findViewById(R.id.b442);
	    b3=(Button)findViewById(R.id.b443);
	    b4=(Button)findViewById(R.id.b444);
	    b5=(Button)findViewById(R.id.b445);
	    b6=(Button)findViewById(R.id.b446);
	    b7=(Button)findViewById(R.id.b447);
	    b8=(Button)findViewById(R.id.b448);
	    b9=(Button)findViewById(R.id.b449);
	    b10=(Button)findViewById(R.id.b4410);
	    b11=(Button)findViewById(R.id.b4411);
	    b12=(Button)findViewById(R.id.b4412);
	    b13=(Button)findViewById(R.id.b4413);
	    b14=(Button)findViewById(R.id.b4414);
	    b15=(Button)findViewById(R.id.b4415);
	    b16=(Button)findViewById(R.id.b4416);
	    random=(Button)findViewById(R.id.randomize);
		function(null);
	
	}
	
	public void function(View v)
	{
		Random d = new Random();
        ArrayList<Integer> ar=new ArrayList<Integer>(5);
        int k;
        
        
        k=d.nextInt(10);
        ar.add(k);
        
        b1.setText(Integer.toString(k+1));
        
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b2.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b3.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b4.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);        
        
        b5.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b6.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b7.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b8.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b9.setText(Integer.toString(k+1));
        
        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b10.setText(Integer.toString(k+1));

        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b11.setText(Integer.toString(k+1));

        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b12.setText(Integer.toString(k+1));

        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b13.setText(Integer.toString(k+1));

        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b14.setText(Integer.toString(k+1));

        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b15.setText(Integer.toString(k+1));

        do{
            k=d.nextInt(16);
            
        }while(ar.contains(k));
        ar.add(k);
        
        
        b16.setText(Integer.toString(k+1));
	}

	
	public void onclickbox(View v)
	{
		Button start=(Button)findViewById(R.id.startgame);
		if(start.getVisibility()==View.INVISIBLE&&gameover==false)
		{
			Button b=(Button) v;
			b.setText("X");
			b.setBackgroundColor(getResources().getColor(R.color.grey));
			
			check_horizontally();
			check_vertically();
			check_diagonally();
			
			if(count==0)
			{
				TV.setText("_ _ _ _ _");
			}
			else if(count==1)
			{
				TV.setText("B _ _ _ _");
			}
			else if(count==2)
			{
				TV.setText("B I _ _ _");
			}
			else if(count==3)
			{
				TV.setText("B I N _ _");
			}
			else if(count==4)
			{
				TV.setText("B I N G _");
			}
			else if(count>=5)
			{
				TV.setText("B I N G O");
				gameover=true;
			}
			count=0;
			
		}
		
	}
	
	public void check_horizontally()
	{
		if(b1.getText()=="X"&&b2.getText()=="X"&&b3.getText()=="X"&&b4.getText()=="X")
		{
			count++;
		}
		if(b5.getText()=="X"&&b6.getText()=="X"&&b7.getText()=="X"&&b8.getText()=="X")
		{
			count++;
		}
		if(b9.getText()=="X"&&b10.getText()=="X"&&b11.getText()=="X"&&b12.getText()=="X")
		{
			count++;
		}
		if(b13.getText()=="X"&&b14.getText()=="X"&&b15.getText()=="X"&&b16.getText()=="X")
		{
			count++;
		}
	}
	
	public void check_vertically()
	{
		if(b1.getText()=="X"&&b5.getText()=="X"&&b9.getText()=="X"&&b13.getText()=="X")
		{
			count++;
		}
		if(b2.getText()=="X"&&b6.getText()=="X"&&b10.getText()=="X"&&b14.getText()=="X")
		{
			count++;
		}
		if(b3.getText()=="X"&&b7.getText()=="X"&&b11.getText()=="X"&&b15.getText()=="X")
		{
			count++;
		}
		if(b4.getText()=="X"&&b8.getText()=="X"&&b12.getText()=="X"&&b16.getText()=="X")
		{
			count++;
		}
	}
	
	public void check_diagonally()
	{
		if(b1.getText()=="X"&&b6.getText()=="X"&&b11.getText()=="X"&&b16.getText()=="X")
		{
			count++;
		}
		if(b4.getText()=="X"&&b7.getText()=="X"&&b10.getText()=="X"&&b13.getText()=="X")
		{
			count++;
		}
	}
	
	public void onclickstart(View v)
	{
		LinearLayout l1=(LinearLayout)findViewById(R.id.ll1);
		l1.removeView(random);
		TextView tv=new TextView(this);
		
		tv.setText("_ _ _ _ _");
		tv.setTextColor(Color.WHITE);
		tv.setBackgroundColor(Color.BLACK);
		tv.setTextSize(35);
		l1.addView(tv);
		TV=tv;
		v.setVisibility(View.INVISIBLE);
	}
	
}

