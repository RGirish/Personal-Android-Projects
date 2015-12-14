package com.example.iclubgallery;


import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends Activity {

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
	
	public void girish(View view)
	{
        int resid=R.drawable.girish;
        ImageView i=(ImageView) findViewById(R.id.imageView1);
        i.setImageResource(resid);
	}
	public void deepak(View view)
	{
        int resid=R.drawable.deepak;
        ImageView i=(ImageView) findViewById(R.id.imageView1);
        i.setImageResource(resid);
	}
	public void narang(View view)
	{
        int resid=R.drawable.narang;
        ImageView i=(ImageView) findViewById(R.id.imageView1);
        i.setImageResource(resid);
	}
	public void khushal(View view)
	{
        int resid=R.drawable.khushal;
        ImageView i=(ImageView) findViewById(R.id.imageView1);
        i.setImageResource(resid);
	}
	public void abishek(View view)
	{
        int resid=R.drawable.abishek;
        ImageView i=(ImageView) findViewById(R.id.imageView1);
        i.setImageResource(resid);
	}
	public void mukesh(View view)
	{
        int resid=R.drawable.mukesh;
        ImageView i=(ImageView) findViewById(R.id.imageView1);
        i.setImageResource(resid);
	}
	public void komal(View view)
	{
        int resid=R.drawable.komal;
        ImageView i=(ImageView) findViewById(R.id.imageView1);
        i.setImageResource(resid);
	}
}
