package com.edu.niu.cs.rosshettel.A6Threads;

import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadsActivity extends Activity {
	private static int barCount = 10;
	private static String logtag = "A6";
	TextView clockText;
	boolean isRunning = false;
	Date date;
	
	Handler clockThread = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			date = new Date();
			Log.d(logtag, "in handleMessage() - " + date.toString());
			clockText.setText(date.toString());
		}
	};
	
	
	
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
        clockText = (TextView)findViewById(R.id.clockText);
        date = new Date();
        
    }
    
    public void onResume() {
    	super.onResume();
    	clockText.setText(date.toString());
    	Log.d(logtag, "in onResume()");
    	    	
    	Thread clock = new Thread(new Runnable() {
    		public void run() {
    			try {
    				//only do this while isRunning is true - aka we haven't hit onPause or onStop yet
    				while(isRunning)
    				{
    					Thread.sleep(1000); //wait one second
    					//call the clockThread handler
    					clockThread.sendMessage(clockThread.obtainMessage());
    				}
				} catch (Throwable t) {
					Log.d(logtag, "we've reached the catch of the clock thread try/catch | " + t.getLocalizedMessage());
					//just end the clock thread
				}
    		}
    	});
    	
    	isRunning = true;
    	clock.start();
    }
    
    public void onPause() {
    	Log.d(logtag, "in onPause()");
    	super.onPause();
    	isRunning = false;
    }
    
    public void onStop() {
    	Log.d(logtag, "in onStop()");
    	super.onStop();
    	isRunning = false;
    }
}