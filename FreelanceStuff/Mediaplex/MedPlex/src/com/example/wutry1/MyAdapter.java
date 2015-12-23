package com.example.wutry1;

import com.example.medplex.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Weather>
{

    Context context;
    int layoutResourceId;   
    Weather data[] = null;
   
    public MyAdapter(Context context, int layoutResourceId, Weather[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
           
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
       try
       {
    	   Weather weather = data[position];
    	   holder.txtTitle.setText(weather.title);
    	   holder.imgIcon.setImageResource(weather.icon);
       }
       catch(Exception e)
       {
    	   Log.i(null, "Girish");
       }
        return row;
    }
   
    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}