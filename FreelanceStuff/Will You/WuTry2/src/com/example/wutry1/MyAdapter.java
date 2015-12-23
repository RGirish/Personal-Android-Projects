package com.example.wutry1;
import com.example.wutry2.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;

  public MyAdapter(Context context, String[] values) {
    super(context, R.layout.main_layout, values);
    this.context = context;
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
    View rowView = inflater.inflate(R.layout.main_layout, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.label);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
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