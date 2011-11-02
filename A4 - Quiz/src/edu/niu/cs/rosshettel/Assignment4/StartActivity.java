/****************************************************************
   PROGRAM:   Assignment 4
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  11/02 at class time

   FUNCTION:  The StartActivity class  sets up the menu interface
              for this program. It creates an options menu. And then
              adds menu items to the menu. Options include: Take the Quiz,
              Study, and the About option.  Any of these options can be chosen
              and in that case each of their own classes will be executed when
              called.

   INPUT:     A Menu selection 

   OUTPUT:    Either the option Take the Quiz, Study, or About will be chosen

   NOTES:     None.
****************************************************************/

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

public class StartActivity extends Activity {
	
    /****************************************************************
		FUNCTION:   void onCreate(Bundle)
		ARGUMENTS:  saved instance state (Bundle)
		RETURNS:    nothing
		NOTES:      This simply sets the layout to main.xml
		            for the menu screen.
    ****************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources res = getResources();
        
    }
    /****************************************************************
		FUNCTION:   boolean onCreateOptionsMenu(android.view.Menu)
		ARGUMENTS:  A passed Menu object
		RETURNS:    true
		NOTES:      This creates an options menu.
    ****************************************************************/
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu passedMenu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, passedMenu);
    	return true;
    }
    /****************************************************************
		FUNCTION:   boolean onOptionsItemSelected(MenuItem)
		ARGUMENTS:  saved instance state (Bundle)
		RETURNS:    true
		NOTES:      This function has a case statement with each
		            of the options listed. If any option is chosen
		            its case statement will execute the class
		            for that option.
    ****************************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    	case R.id.about:
    		Log.d("A4_debug", "About selected");
    		Intent about = new Intent(this, About.class);
                startActivity(about);
    		break;
    	case R.id.study:
    		Intent studyTime = new Intent(this, Internet.class);
    		startActivity(studyTime);
    		break;
    	case R.id.take_quiz:
    		Log.d("A4_debug", "Take quiz selected");
    		Intent quiz = new Intent(this, Quiz.class);
    		startActivity(quiz);
    		break;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    	return true;
    }
}