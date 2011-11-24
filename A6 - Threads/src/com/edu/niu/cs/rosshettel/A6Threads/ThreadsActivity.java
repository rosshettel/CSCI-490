package com.edu.niu.cs.rosshettel.A6Threads;

import java.util.Date;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadsActivity extends Activity {
	//final/static variables
	private static String logtag = "A6";
	private static final int flipcards_XML = R.xml.flipcards;
	private static final int sortWhat = 1;
	private static final int clockWhat = 2;
	
	private int debugClockCounter = 0;
	flipCardCollection flipcards;
	boolean isRunning = false;
	boolean isSortRunning = false;
	
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
        
        //set up the sort
        sortSetup();
        
        //set up the flipcards
        flipCardSetup();
        
        //lets set up the clock thread
        clockText = (TextView)findViewById(R.id.clockText);
        
    }
    
    public void sortSetup() {
    	btnSort = (Button)findViewById(R.id.sortButton);
    	btnReset = (Button)findViewById(R.id.resetButton);
    	
    	//get the layout containing all the progress bars
        LinearLayout progressBarViews = (LinearLayout)findViewById(R.id.progressBars);
        //set up the array of bars
        bars = new ProgressBar[progressBarViews.getChildCount()];
        //loop through the layout and add the bars to the array
        for(int i = 0; i < progressBarViews.getChildCount(); i++)
        	bars[i] = (ProgressBar)progressBarViews.getChildAt(i);
        
        //initialize the bars to random values
        bars = resetBars(bars);
        
        //set the sort buttons onclicklistener
        btnSort.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v)
        	{
//        		bars = sortBars(bars);
        		
        		Thread sort = new Thread(new Runnable() {
        			public void run() {
        				try {
        					while(isSortRunning)
        					{
        						int i = 0;
        						int j = 0;
        						int min = 0;
        						
        						for(i = 0; i < bars.length - 1; i++)
        				    	{
        				    		min = i;
        				    		for(j = i + 1; j < bars.length; j++)
        				    		{
        				    			if(bars[j].getProgress() < bars[min].getProgress())
        				    				min = j;
        				    		}
        				    		if(min != i)
        				    		{
        				    			//this is where we swap
        								Log.d(logtag, "swapping two bars: "+i+" and "+min);
        								Thread.sleep(500); //sleep half a second
        								handler.sendMessage(handler.obtainMessage(sortWhat, i, min));
        				    		}
        				    	}
        						
        						isSortRunning = false;
        						
        						Log.d(logtag, "we finished sorting - now breaking. i="+i+" / j="+j+" / min="+min);
//        						break; //exit the thread now that we've sorted it
        					}
        				} catch (Throwable t) {
        					Log.d(logtag, "we've reached the catch of the sort thread try/catch | " + t.getLocalizedMessage());
        					//just end the sort thread
        				}
        			}
        		});
        		
        		isSortRunning = true;
        		sort.start();
        	}
        });
        
        //set the reset buttons onclicklistener
        btnReset.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) { resetBars(bars); }
        });
    }
    
    Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case sortWhat:
				{
					//arg1 == i, arg2 == min
					int tmp = bars[msg.arg1].getProgress();
					bars[msg.arg1].setProgress(bars[msg.arg2].getProgress());
					bars[msg.arg2].setProgress(tmp);
					break;
				}
				case clockWhat:
				{
					Date date = new Date();	//sometimes java can be a bit... redundant.
					clockText.setText(debugClockCounter + " | " + date.toString());
					debugClockCounter++;
					break;
				}
			}
		}
	};
    
    public ProgressBar[] sortBars(ProgressBar[] bars)
    {
    	int i, j, min, tmp;
    	
    	for(i = 0; i < bars.length - 1; i++)
    	{
    		min = i;
    		for(j = i + 1; j < bars.length; j++)
    		{
    			if(bars[j].getProgress() < bars[min].getProgress())
    				min = j;
    		}
    		if(min != i)
    		{
    			tmp = bars[i].getProgress();
    			bars[i].setProgress(bars[min].getProgress());
    			bars[min].setProgress(tmp);
    		}
    	}
    	
    	return bars;
    }
    
    public ProgressBar[] resetBars(ProgressBar[] bars)
    {
    	Random generator = new Random();
    	
    	for(int i = 0; i < bars.length; i++)
    		bars[i].setProgress(generator.nextInt(100) + 1);
    	
    	return bars;
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
    	
    	//set the remove buttons onclicklistener
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
    					handler.sendMessage(handler.obtainMessage(clockWhat));
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
    	debugClockCounter = 0;
    }
    
    public void onStop() {
    	Log.d(logtag, "in onStop()");
    	super.onStop();
    	isRunning = false;
    	debugClockCounter = 0;
    }
}
    