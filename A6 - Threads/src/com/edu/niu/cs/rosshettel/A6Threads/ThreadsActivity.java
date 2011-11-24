package com.edu.niu.cs.rosshettel.A6Threads;

import java.util.Date;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadsActivity extends Activity {
	//final/static variables
	private static String logtag = "A6";
	private static final int flipcards_XML = R.xml.flipcards;
	
	flipCardCollection flipcards;
	boolean isRunning = false;
	
	//the various views
	TextView clockText, cardDisplay;
	Button btnSort, btnReset;
	Button btnRestart, btnFlip, btnNext, btnRemove;
	ProgressBar[] bars;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //get the layout containing all the progress bars
        LinearLayout progressBarViews = (LinearLayout)findViewById(R.id.progressBars);
        //set up the array of bars
        bars = new ProgressBar[progressBarViews.getChildCount()];
        //loop through the layout and add the bars to the array
        for(int i = 0; i < progressBarViews.getChildCount(); i++)
        	bars[i] = (ProgressBar)progressBarViews.getChildAt(i);
        
        Log.d(logtag, "were about to start on creating the flipcards");  
        flipCardSetup();
        
        //lets set up the clock thread
        clockText = (TextView)findViewById(R.id.clockText);
        
    }
    
    /**
     * this contains all the code for managing the flipcard ui. 
     * I put it into it's own function so it would be easier to manage. And I don't 
     * like an onCreate that's like 800 lines long
     */
    public void flipCardSetup() {
    	cardDisplay = (TextView)findViewById(R.id.cardDisplay);
    	btnRestart = (Button)findViewById(R.id.reStartButton);
    	btnFlip = (Button)findViewById(R.id.flipButton);
    	btnNext = (Button)findViewById(R.id.nextButton);
    	btnRemove = (Button)findViewById(R.id.removeButton);
    	
    	//set up the flipcards from xml
    	XmlPullParser xpp;
    	xpp = getResources().getXml(flipcards_XML);
    	flipcards = new flipCardCollection(xpp);
    	
    	//set up the first card
    	cardDisplay.setText(flipcards.getFrontText());
    	
    	//set the reStart buttons onclicklistener
    	btnRestart.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			flipcards.moveToBeginning();
    			cardDisplay.setText(flipcards.getFrontText());
        	}
    	});
    	
    	//set the flip buttons onclicklistener
    	btnFlip.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			cardDisplay.setText(flipcards.getFlipText((String)cardDisplay.getText()));
    		}
    	});
    	
    	//set the next buttons onclicklistener
    	btnNext.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			cardDisplay.setText(flipcards.nextCard());
    		}
    	});
    	
    	btnRemove.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			flipcards.removeCard();
    			//calling getfronttext should display the front text of the next card
    			cardDisplay.setText(flipcards.getFrontText());
    		}
    	});
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
    
    Handler clockThread = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Date date = new Date();	//sometimes java can be a bit... redundant.
//			Log.d(logtag, "in handleMessage() - " + date.toString());
			clockText.setText(date.toString());
		}
	};
    
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