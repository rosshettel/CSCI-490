package com.edu.niu.cs.rosshettel.A6Threads;

import java.util.Date;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadsActivity extends Activity {
	private static String logtag = "A6";
	private static final int flipcards_XML = R.xml.flipcards;
	
	TextView clockText;
	flipCardCollection flipcards;
	boolean isRunning = false;
	
	
	Handler clockThread = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Date date = new Date();	//sometimes java can be a bit... redundant.
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
        
        Log.d(logtag, "were about to start on creating the flipcards");
        //lets create the flipcard collection object
        XmlPullParser xpp;
        xpp = getResources().getXml(flipcards_XML);
        flipcards = new flipCardCollection(xpp);
        
        //lets set up the clock thread
        clockText = (TextView)findViewById(R.id.clockText);
        
    }
    
    public void onResume() {
    	super.onResume();
    	clockText.setText(new Date().toString());	//set the first time
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
    
    public void onDestroy() {
    	Log.d(logtag, "we reached onDestroy()");
    	super.onDestroy();
    }
}