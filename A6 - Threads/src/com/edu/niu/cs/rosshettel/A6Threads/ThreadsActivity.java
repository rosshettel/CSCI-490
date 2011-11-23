package com.edu.niu.cs.rosshettel.A6Threads;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadsActivity extends Activity {
	private static int barCount = 10;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //get the layout containing all the progress bars
        LinearLayout progressBarViews = (LinearLayout)findViewById(R.id.progressBars);
        //set up the array of bars
        ProgressBar[] bars = new ProgressBar[progressBarViews.getChildCount()];
        //loop through the layout and add the bars to the array
        for(int i = 0; i < progressBarViews.getChildCount(); i++)
        	bars[i] = (ProgressBar)progressBarViews.getChildAt(i);
        
        
        //lets set up the clock thread
        TextView clockText = (TextView)findViewById(R.id.clockText);
        
        Thread clock = new Thread(new Runnable()
        {
        	public void run()
        	{
        		
        	}
        });
        
        
        
        
    }
}