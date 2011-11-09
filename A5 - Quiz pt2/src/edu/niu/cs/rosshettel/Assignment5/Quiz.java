/****************************************************************
   PROGRAM:   Assignment 4
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  11/02 at class time

   FUNCTION:  The Quiz class implements the XML reading as well as the 
              layout and quiz questions.

   INPUT:     XML file, user input.

   OUTPUT:    Quiz questions and answers.

   NOTES:     None.
****************************************************************/
package edu.niu.cs.rosshettel.Assignment5;

import java.io.IOException;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends Activity {
	private String LOG_TAG = "A4_debug";
	
	Button checkButton, nextButton; //quiz buttons
	
	//here's the radio buttons and the group for them
	RadioButton rbutton1, rbutton2, rbutton3, rbutton4;
	RadioGroup buttonGroup;
	
	//here's the array of questions
	Question[] quizQuestions;
	TextView questionTextView;
	
	//this is the XML file
	static final int XML_FILE = R.xml.questions;
	
	//this is the number of possible choices
	static final int NUM_CHOICES=4;
	//keeps track of the current question index
	static int currentQuestion;
	
	SQLiteDatabase quizDB;
	static final String createStr = "CREATE TABLE quiz_table (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"question TEXT, " +
			"ans1 TEXT, " +
			"ans2 TEXT, " +
			"ans3 TEXT, " +
			"ans4 TEXT, " +
			"correctAns INT)";

    /****************************************************************
    	FUNCTION:   void onCreate(Bundle)
		ARGUMENTS:  saved instance state (Bundle)
		RETURNS:    nothing
		NOTES:      This sets the initial layout, checks the user's
		            response to see if it is correct, and loads the
		            next question when the user clicks the Next button.
     ****************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.quiz);
	    buttonGroup = (RadioGroup)findViewById(R.id.radioGroup1);
	    currentQuestion=0;
	    int isTable = 0;
	    
	    quizDB = openOrCreateDatabase("quizDB.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	    quizDB.setLocale(Locale.getDefault());
	    quizDB.setLockingEnabled(true);
	    quizDB.setVersion(1);
	    
	    //check if the table actually exists yet
//	    Cursor hazTable = quizDB.rawQuery("SELECT COUNT() FROM 'quiz_table'", null); 
	    if(!doesTableExist("quiz_table"))
	    {
	    	Log.d(LOG_TAG, "im now building the table from the xml");
	    	loadXML();
	    	buildTableFromQuestions(quizDB, quizQuestions);
	    }	    
	    
	    //load the questions from sql
	    quizQuestions = loadQuestionsFromSQL(quizDB);
	    
	    //load the initial question
	    nextQuestion(quizQuestions[0]);

	    
	    //set up button to check user's answer
	    checkButton = (Button)findViewById(R.id.button1);
	    //Here's the onClickListener for the check answer button.  It compares the user's
	    //answer to the correct answer in the Question class.  It pops up a toast message
	    //whether the answer is correct or not.  It also controls whether the next question
	    //button is clickable.  It is only clickable after the user has entered a correct answer.
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
	    
	    //set up button to move to next question
	    nextButton  = (Button)findViewById(R.id.button2);
	    //should only be enabled after user has entered a correct answer.
	    nextButton.setEnabled(false);
	    //Here's the onClickListener for the next question button.  It loads the next
	    //question and its answer candidates.  If there are no more questions, it pops
	    //up a toast message and launches the Goodbye activity.
	    nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				currentQuestion++;
				//if not end of array, load next question
				Log.d(LOG_TAG, "Index is: " + currentQuestion);
				Log.d(LOG_TAG, "Array length is: " + quizQuestions.length);
				
				if(currentQuestion < quizQuestions.length)
				{
					//set the text to the next question
					nextQuestion(quizQuestions[currentQuestion]);
				    
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
	
	public boolean doesTableExist(String tableName)
	{
		Cursor rs = null;
		try {
			rs = quizDB.rawQuery("SELECT * FROM " + tableName + " WHERE 1 = 0", null);
			return true;
		} catch (Exception ex) {
			return false;
		} finally {
			if (rs != null)
				rs.close();
		}
	}
	
	public Question[] loadQuestionsFromSQL(SQLiteDatabase db)
	{
		
		Cursor cur;
		Question[] questionArray;
		
		cur = db.rawQuery("SELECT * FROM quiz_table", null);
		questionArray = new Question[cur.getCount()];
		int i = 0;
		
		//loop through the results
		cur.moveToFirst();
		while(!cur.isAfterLast())
		{
			questionArray[i] = new Question();
			questionArray[i].setQuestion(cur.getString(1));
			questionArray[i].setAnswer(cur.getInt(6));
			for(int c = 0; c < 4; c++)
				questionArray[i].setCandidates(c, cur.getString(c + 2));
			i++;
			cur.moveToNext();
		}
		return questionArray;
	}
	
	public void buildTableFromQuestions(SQLiteDatabase db, Question[] questions)
	{
		//first try creating the database
		try { db.execSQL(createStr); }
		catch(SQLException e)
		{
			Log.d(LOG_TAG, "Error creating DB: " + e.getMessage());
			Toast.makeText(Quiz.this, "Error creating DB!", Toast.LENGTH_LONG).show();
		}
		
		ContentValues values = new ContentValues();
		
		//step through and populate the database from the array of questions
		for(int i = 0; i <= questions.length - 1; i++)
		{
			try {
				values.put("question", questions[i].getQuestion());
				values.put("ans1", questions[i].getCandidate(0));
				values.put("ans2", questions[i].getCandidate(1));
				values.put("ans3", questions[i].getCandidate(2));
				values.put("ans4", questions[i].getCandidate(3));
				values.put("correctAns", questions[i].getCorrectAnswer());
				
				db.insert("quiz_table", null, values);
			} catch(SQLException e) {
				Log.d(LOG_TAG, "Sql Exception: " + e.getMessage());
				//deal with this error here
			} catch(NullPointerException n) {
				Log.d(LOG_TAG, "Null pointer exception: " + n.getMessage());
			}
		}		
	}
	
	public void nextQuestion(Question newQuestion)
	{
		//get the ids of the text fields we want to change
		questionTextView = (TextView)findViewById(R.id.textView1);
		rbutton1 = (RadioButton)findViewById(R.id.radioButton1);
	    rbutton2 = (RadioButton)findViewById(R.id.radioButton2);
	    rbutton3 = (RadioButton)findViewById(R.id.radioButton3);
	    rbutton4 = (RadioButton)findViewById(R.id.radioButton4);
	    
	    //now set the text fields to the question's data
	    questionTextView.setText(newQuestion.getQuestion());
	    rbutton1.setText(newQuestion.getCandidate(0));
	    rbutton2.setText(newQuestion.getCandidate(1));
	    rbutton3.setText(newQuestion.getCandidate(2));
	    rbutton4.setText(newQuestion.getCandidate(3));
	}

    /****************************************************************
		FUNCTION:   boolean loadXML()
		ARGUMENTS:  none
		RETURNS:    boolean, false if XML wasn't parsed properly
		NOTES:      This grabs the quiz questions, answers, and answer
		            candidates from the XML file.
     ****************************************************************/
	public boolean loadXML()
	{
		//load XML file
		XmlPullParser xpp;
		xpp = getResources().getXml(XML_FILE);		
		
		//counters to keep track of where you are in the array of Questions.
		int currentCounter=0;
		int questionCounter=0;
		
		try{
			//lets load the quiz data from the xml file
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
			{
				//do stuff if found the start tag
				if(xpp.getEventType() == XmlPullParser.START_TAG)
				{
					//set up quiz questions array
					if(xpp.getName().equals("quiz"))
					{
						int temp = Integer.parseInt(xpp.getAttributeValue(1));
						quizQuestions = new Question[temp+1];
					}
					
					//grab question from XML file
					if(xpp.getName().equals("question"))
					{
						//initialize new question
						quizQuestions[questionCounter] = new Question();
						
						Log.d(LOG_TAG, "name: " + (String)xpp.getAttributeValue(0) + " | question counter: " + questionCounter);
						
						//set questions and correct answer
						quizQuestions[questionCounter].setQuestion((String)xpp.getAttributeValue(1));
						quizQuestions[questionCounter].setAnswer(Integer.parseInt(xpp.getAttributeValue(2)));
						questionCounter++;
					}	
					//grab candidate questions
					if(xpp.getName().equals("candidate"))
					{
						quizQuestions[questionCounter-1].setCandidates(currentCounter, (String)xpp.getAttributeValue(0));
						currentCounter++;
					}
					//reset counter if you get to the maximum number of choices specified above.
					if(currentCounter == NUM_CHOICES)
					{
						currentCounter = 0; //reset counter
					}
					
				}
				
				//move to next XML tag
				try {
					xpp.next();
				} catch (IOException e) {
					Log.e("A4_debug", "IOException: " + e.getMessage());
				}
			}
		}
		//catch the XML exception if there's a problem
		catch(XmlPullParserException e)
		{
			Log.e("A4_debug", "XmlPullParserException: " + e.getMessage());
			return false;
		}
		return true;
	}

}
