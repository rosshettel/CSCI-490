package edu.niu.cs.rosshettel.mortgage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MortgageCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spinner = (Spinner) findViewById(R.id.term_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		this, R.array.term_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
