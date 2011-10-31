package edu.niu.cs.rosshettel.Assignment4;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    	case R.id.about:
    		Log.d("A4_debug", "About selected");
    		Intent about = new Intent(this, About.class);
            startActivity(about);
    		return true;
    	case R.id.study:
    		Intent studyTime = new Intent(this, Internet.class);
    		startActivity(studyTime);
    	case R.id.take_quiz:
    		Log.d("A4_debug", "Take quiz selected");
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
}