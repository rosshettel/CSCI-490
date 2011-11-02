package edu.niu.cs.rosshettel.Assignment4;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Quiz extends Activity {
	//my fancy way of making a global log tag
	private final String LOG_TAG = getResources().getString(R.string.logtag);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.quiz);
	
	}
	
	public boolean loadXML(int quizXML)
	{
		XmlPullParser xpp;
		xpp = getResources().getXml(quizXML);
		try{
			//lets load the quiz data from the xml file
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
			{
				if(xpp.getEventType() == XmlPullParser.START_TAG)
				{
					if(xpp.getName().equals("question"))
					Log.i(LOG_TAG, (String)xpp.getAttributeName(1));
					Log.i(LOG_TAG, (String)xpp.getAttributeValue(1));
					
					Log.i(LOG_TAG, (String)xpp.getAttributeName(2));
					Log.i(LOG_TAG, (String)xpp.getAttributeValue(2));
				}
				
				try {
					xpp.next();
				} catch (IOException e) {
					Log.e(LOG_TAG, "IOException: " + e.getMessage());
				}
			}
		}
		catch(XmlPullParserException e)
		{
			Log.e(LOG_TAG, "XmlPullParserException: " + e.getMessage());
			//maybe display a toast message here as well?
			return false;
		}
		
		return true;
	}

}
