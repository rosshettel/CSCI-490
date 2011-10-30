package edu.niu.cs.rosshettel.Assignment4;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuInflater;
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
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu passedMenu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, passedMenu);
    	return true;
    }
}