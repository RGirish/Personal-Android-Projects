
package com.example.rain;

import com.example.yuva.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String>
{
	private final Context context;
	  private final int resid;
	  private String[] values=null;

	  public MyAdapter(Context context, int id ,String[] values)
	  {
	    super(context, id);
	    this.context = context;
	    this.resid = id;
	    this.values = values;
	  }

	  
	  @Override
	  public int getCount()
	  {
		return 35;
	  }
	  
	  
	  
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) 
	  {
		String s = values[position];
		    
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(resid, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.txtTitle);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.imgIcon);
	    
	    Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/akshar.ttf");
	    textView.setTypeface(tf);
	    
	    if(position==0||position==4||position==8||position==12||position==16||position==20||position==24||position==28||position==32)
	    {
	    	rowView.setBackgroundColor(context.getResources().getColor(R.color.color1));
	    }
	    else if(position==1||position==5||position==9||position==13||position==17||position==21||position==25||position==29||position==33)
	    {
	    	rowView.setBackgroundColor(context.getResources().getColor(R.color.color4));
	    }
	    else if(position==2||position==6||position==10||position==14||position==18||position==22||position==26||position==30||position==34)
	    {
	    	rowView.setBackgroundColor(context.getResources().getColor(R.color.color3));
	    }
	    else if(position==3||position==7||position==11||position==15||position==19||position==23||position==27||position==31||position==35)
	    {
	    	rowView.setBackgroundColor(context.getResources().getColor(R.color.color2));
	    }
	    
	    
	    
	    textView.setText(values[position]);
	    if(s!=null)
	    {
	    
	    try
	    {
	    if (s.startsWith("Photo")) 
	    {
	    	imageView.setImageResource(R.drawable.picture);
	    }
	    else if (s.startsWith("Video")) 
	    {
	    	imageView.setImageResource(R.drawable.video);
	    }
	    else 
	    {
	    	imageView.setImageResource(R.drawable.quotation);
	    }
	    }
	    catch(Exception e)
	    {
	    	Log.i("Girish","Exception");
	    }
	    
	    }
	    return rowView;
	  }
	  
	  
	  
	  
	  
}
