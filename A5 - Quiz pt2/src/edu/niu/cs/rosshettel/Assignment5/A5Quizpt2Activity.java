/****************************************************************
   PROGRAM:   Assignment 5
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  11/09 at class time

   FUNCTION:  The A5Quizpt2Activity sets the default screen when the
              app is opened.

   INPUT:     None.

   OUTPUT:    Home screen.

   NOTES:     None.
****************************************************************/

package edu.niu.cs.rosshettel.Assignment5;

import android.app.Activity;
import android.os.Bundle;

public class A5Quizpt2Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}