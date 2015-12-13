
package com.example.cognit1;

import java.util.Date;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider 
{

  @SuppressWarnings("deprecation")
@Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
  {
    ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    
    Date d=new Date();
    int days_rem;
    String message="";
    int month=d.getMonth();
    if(month==8&&d.getDate()>=21)
    {
    	message="Cognit'13: THE WAIT IS OVER!";
    }
    else if(month==7)
    {
    	days_rem=31-d.getDate()+21;
    	message="Cognit'13: "+days_rem+" more days to go!";
    }
    else if(month==8)
    {
    	days_rem=21-d.getDate();
    	message="Cognit'13: "+days_rem+" more days to go!";
    	if(days_rem==1){message=days_rem+" more day to go!";}
    }
    else if(month>8)
    {
    	message="Cognit'13: THE WAIT IS OVER!";
    }
    
    
    
    for (int widgetId : allWidgetIds) 
    {
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
      remoteViews.setTextViewText(R.id.mytextview, message);
      
      Intent intent = new Intent(context, MainActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
      remoteViews.setOnClickPendingIntent(R.id.mytextview, pendingIntent);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
  }
}
