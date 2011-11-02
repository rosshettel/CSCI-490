package edu.niu.cs.rosshettel.Assignment4;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Quiz extends Activity {
	//my fancy way of making a global log tag
	//private final String LOG_TAG = getResources().getString(R.string.logtag);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.quiz);
	    loadXML();
	
	}
	
	public boolean loadXML()
	{
		XmlPullParser xpp;
		xpp = getResources().getXml(R.xml.questions);
		TextView text = new TextView(this);
		text = (TextView)findViewById(R.id.textView1);
		RadioGroup radioG = (RadioGroup)findViewById(R.id.radioGroup1);
		RadioButton radio1 = new RadioButton(this);
		radio1 = (RadioButton)findViewById(R.id.radioButton1);
		RadioButton radio2 = new RadioButton(this);
		radio2 = (RadioButton)findViewById(R.id.radioButton2);
		RadioButton radio3 = new RadioButton(this);
		radio3 = (RadioButton)findViewById(R.id.radioButton3);
		RadioButton radio4 = new RadioButton(this);
		radio4 = (RadioButton)findViewById(R.id.radioButton4);
		
		try{
			//lets load the quiz data from the xml file
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
			{
				if(xpp.getEventType() == XmlPullParser.START_TAG)
				{
					if(xpp.getName().equals("question"))
					{
						text.setText((String)xpp.getAttributeValue(1));
						radio1.setText((String)xpp.getAttributeValue(2));
						Log.i("A4_debug", (String)xpp.getAttributeName(1));
						Log.i("A4_debug", (String)xpp.getAttributeValue(1));	
						Log.i("A4_debug", (String)xpp.getAttributeName(2));
						Log.i("A4_debug", (String)xpp.getAttributeValue(2));
						//radio button listener here possibly
					}
				}
				
				try {
					xpp.next();
				} catch (IOException e) {
					Log.e("A4_debug", "IOException: " + e.getMessage());
				}
			}
		}
		catch(XmlPullParserException e)
		{
			Log.e("A4_debug", "XmlPullParserException: " + e.getMessage());
			//maybe display a toast message here as well?
			return false;
		}
		
		return true;
	}

}
