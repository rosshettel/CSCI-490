package edu.niu.cs.rosshettel.Assignment4;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
//test
public class StartActivity extends Activity {
    TextView title;
    Button takeQuiz, surfWeb, aboutButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources res = getResources();
        
        //make a label of this
        title = (TextView) findViewById(R.id.title_message);
        takeQuiz = (Button) findViewById(R.id.quiz_button);
        surfWeb = (Button) findViewById(R.id.internet_button);
        aboutButton = (Button) findViewById(R.id.about_button);
        
    }
}