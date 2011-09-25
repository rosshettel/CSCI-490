package edu.niu.cs.rosshettel.mortgage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MortgageCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }
    
    /*
     * Grab the data from the user inputs, and calculate the mortgage rate
     * John Miller makes an edit here to test github.
     */
    public void calculateMortgage(View theButton) {
    	//do something intelligent here
    	Log.v("debugging", "we clicked the calculate button!");
    }
    
    /*
     * Clear all the input data
     */
    public void clearInputs(View theButton) {
    	//wipe it all clean
    }
}
