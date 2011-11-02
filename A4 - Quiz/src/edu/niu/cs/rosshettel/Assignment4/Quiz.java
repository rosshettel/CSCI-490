package edu.niu.cs.rosshettel.Assignment4;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends Activity {
	//my fancy way of making a global log tag
	private String LOG_TAG = "A4_debug";
	Button checkButton, nextButton;
	RadioButton rbutton1, rbutton2, rbutton3, rbutton4;
	RadioGroup buttonGroup;
	Question[] quizQuestions;
	TextView questionTextView;
	//this is the XML file
	static final int XML_FILE = R.xml.questions;
	//this is the number of possible choices
	static final int NUM_CHOICES=4;
	
	static int currentQuestion;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.quiz);
	    buttonGroup = (RadioGroup)findViewById(R.id.radioGroup1);
	    currentQuestion=0;
	    loadXML();

	    //set up initial question
	    questionTextView = (TextView)findViewById(R.id.textView1);
	    questionTextView.setText(quizQuestions[0].getQuestion());
	    
	    //set up initial answer candidates
	    rbutton1 = (RadioButton)findViewById(R.id.radioButton1);
	    rbutton1.setText(quizQuestions[0].getCandidate(0));
	    rbutton2 = (RadioButton)findViewById(R.id.radioButton2);
	    rbutton2.setText(quizQuestions[0].getCandidate(1));
	    rbutton3 = (RadioButton)findViewById(R.id.radioButton3);
	    rbutton3.setText(quizQuestions[0].getCandidate(2));
	    rbutton4 = (RadioButton)findViewById(R.id.radioButton4);
	    rbutton4.setText(quizQuestions[0].getCandidate(3));
	    
	    checkButton = (Button)findViewById(R.id.button1);
	    checkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(LOG_TAG, "Checked is: " + buttonGroup.getCheckedRadioButtonId());
				if(quizQuestions[currentQuestion].checkAnswer(buttonGroup.indexOfChild(
								findViewById(buttonGroup.getCheckedRadioButtonId()))))
				{
					Toast.makeText(Quiz.this, "Correct!", Toast.LENGTH_SHORT).show();
					nextButton.setEnabled(true);
				}
				else
				{
					Toast.makeText(Quiz.this, "Incorrect answer", Toast.LENGTH_SHORT).show();
					nextButton.setEnabled(false);
				}
			}
		});
	    
	    nextButton  = (Button)findViewById(R.id.button2);
	    nextButton.setEnabled(false);
	    nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				currentQuestion++;
				//if not end of array, load next question
				Log.d(LOG_TAG, "Index is: " + currentQuestion);
				Log.d(LOG_TAG, "Array length is: " + quizQuestions.length);
				if(currentQuestion < quizQuestions.length-1)
				{
				    //set up next question
				    questionTextView.setText(quizQuestions[currentQuestion].getQuestion());
				    
				    //set up next answer candidates
				    rbutton1.setText(quizQuestions[currentQuestion].getCandidate(0));
				    rbutton2.setText(quizQuestions[currentQuestion].getCandidate(1));
				    rbutton3.setText(quizQuestions[currentQuestion].getCandidate(2));
				    rbutton4.setText(quizQuestions[currentQuestion].getCandidate(3));
				    nextButton.setEnabled(false);
				    
				    //reset radio buttons
				    buttonGroup.clearCheck();
				}
				else
				{
					Log.d(LOG_TAG, "Got to else.");
					Toast.makeText(Quiz.this, "You're done!", Toast.LENGTH_LONG).show();
					nextButton.setEnabled(false);
					Intent goodbye = new Intent(Quiz.this, Goodbye.class);
		            startActivity(goodbye);
				}
			}
		});
	
	}
	
	public boolean loadXML()
	{
		XmlPullParser xpp;
		xpp = getResources().getXml(XML_FILE);		
		
		int currentCounter=0;
		int questionCounter=0;
		
		try{
			//lets load the quiz data from the xml file
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
			{
				if(xpp.getEventType() == XmlPullParser.START_TAG)
				{
					//set up arrays
					if(xpp.getName().equals("quiz"))
					{
						int temp = Integer.parseInt(xpp.getAttributeValue(1));
						quizQuestions = new Question[temp+1];
					}
					if(xpp.getName().equals("question"))
					{
						quizQuestions[questionCounter] = new Question();
						
						Log.d(LOG_TAG, "name: " + (String)xpp.getAttributeValue(0) + " | question counter: " + questionCounter);
						
						quizQuestions[questionCounter].setQuestion((String)xpp.getAttributeValue(1));
						quizQuestions[questionCounter].setAnswer(Integer.parseInt(xpp.getAttributeValue(2)));
						questionCounter++;
					}	
					//radio button listener here possibly
					if(xpp.getName().equals("candidate"))
					{
						quizQuestions[questionCounter-1].setCandidates(currentCounter, (String)xpp.getAttributeValue(0));
						currentCounter++;
					}
					if(currentCounter == NUM_CHOICES)
					{
						currentCounter = 0; //reset counter
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
