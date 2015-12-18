package com.example.letmetellyou;


import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ActivitySwipeDetector extends Activity implements OnTouchListener {

static final String logTag = "ActivitySwipeDetector";
static final int MIN_DISTANCE = 100;
private float downX, downY, upX, upY;

public ActivitySwipeDetector(Activity activity){}

public void onRightToLeftSwipe()
{
    MainActivity.speakOutTheDay();
}

public void onLeftToRightSwipe()
{
    MainActivity.speakOutTheTime();
}

public void onTopToBottomSwipe()
{
    MainActivity.speakOutTheDate();
}

public void onBottomToTopSwipe()
{
	
}

public boolean onTouch(View v, MotionEvent event) {
    switch(event.getAction()){
        case MotionEvent.ACTION_DOWN: {
            downX = event.getX();
            downY = event.getY();
            return true;
        }
        case MotionEvent.ACTION_UP: {
            upX = event.getX();
            upY = event.getY();

            float deltaX = downX - upX;
            float deltaY = downY - upY;

            if(Math.abs(deltaX) > MIN_DISTANCE)
            {            	
                if(deltaX < 0) { this.onLeftToRightSwipe(); return true; }
                if(deltaX > 0) { this.onRightToLeftSwipe(); return true; }
            }
            
            else if(Math.abs(deltaY) > MIN_DISTANCE)
            {
            	if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
                if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
            }
            return true;
        }
    }
    return false;
}

}