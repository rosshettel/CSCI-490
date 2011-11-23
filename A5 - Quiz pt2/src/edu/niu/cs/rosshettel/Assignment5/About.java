/****************************************************************
   PROGRAM:   Assignment 5
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  11/09 at class time

   FUNCTION:  The About class simply sets the content view
              to the About page of the program. The details
              are inside the xml.

   INPUT:     None.

   OUTPUT:    The About page of the program.

   NOTES:     None.
****************************************************************/

package edu.niu.cs.rosshettel.Assignment5;

import android.app.Activity;
import android.os.Bundle;

public class About extends Activity {
     /****************************************************************
        FUNCTION:   void onCreate(Bundle)
		ARGUMENTS:  saved instance state (Bundle)
		RETURNS:    nothing
		NOTES:      This simply changes the layout to the about
                            page. Thats it.
     ****************************************************************/
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.about);
        }

}