/****************************************************************
   PROGRAM:   Assignment 4
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  11/02 at class time

   FUNCTION:  This the Internet class creates an Internet object
              with the url set to a wikipedia article on ipod's.
              This function is used when the user wishes to study
              for the quiz.

   INPUT:     NONE

   OUTPUT:    It pops up the ipod wikipedia article

   NOTES:     None.
****************************************************************/
package edu.niu.cs.rosshettel.Assignment4;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
//test
public class Internet extends Activity {
    
    WebView bowser;
      
    /****************************************************************
   	FUNCTION:   void onCreate(Bundle)
		ARGUMENTS:  saved instance state (Bundle)
		RETURNS:    nothing
		NOTES:      This will create a web browser and add a url to it.
     ****************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet);
        bowser=(WebView)findViewById(R.id.webkit);
        
        //make the browser show the wikipedia article on ipods
        bowser.loadUrl("http://en.wikipedia.org/w/index.php?title=IPod&useformat=mobile");
        
    }
}