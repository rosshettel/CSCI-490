/****************************************************************
   FILE:      Investment.java
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  10/17 at class time

   PURPOSE:   This will set up the Investment tab as well as process any
              input entered here.
****************************************************************/
package edu.niu.cs.rosshettel.Assignment3;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Investment extends Activity
{
	EditText investPrinciple, investInterest, investTerm;
    Button investCalculate, clear;
    TextView outputFinalValue, investOutputError;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2);
		
		investPrinciple = (EditText) findViewById(R.id.amount_invest_input);
        investInterest = (EditText) findViewById(R.id.rate_invest_input);
        investTerm = (EditText) findViewById(R.id.time_invest_input);
        
        investCalculate = (Button) findViewById(R.id.calculate_button_invest);
        clear = (Button) findViewById(R.id.clear_button_invest);
        
        outputFinalValue = (TextView) findViewById(R.id.final_value_ouput);
        investOutputError = (TextView) findViewById(R.id.interest_error);
        
        investCalculate.setOnClickListener(new View.OnClickListener()
        {
        		public void onClick(View v)
        		{
        				calculateInvestment(v);
        		}
        });
        clear.setOnClickListener(new View.OnClickListener()
        {
        		public void onClick(View v)
        		{
        				clearInvestInputs(v);
        		}
        });
	}
    /****************************************************************
		FUNCTION:   void calculateInvestment(View theButton)
		ARGUMENTS:  view
		RETURNS:    none
		NOTES:      Get all the user inputs, convert to respective data types,
					then calculate the final value and output.
     ****************************************************************/
    public void calculateInvestment(View theButton) {
    	double principle_num = 0;
    	double interest_num = 0;
    	double finalValue, interestPercent;
    	int term_num = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        investOutputError.setText(""); //reset the error messages
        
        //try to convert the user input to doubles/int, error if exception found
    	try {
    		principle_num = Double.parseDouble(investPrinciple.getText().toString());
    		interest_num = Double.parseDouble(investInterest.getText().toString());
    		interestPercent = interest_num / 100; //turn the whole into a decimal percentage
    		term_num = Integer.parseInt(investTerm.getText().toString());
    	} catch (final NumberFormatException e) {
    		Log.d("A2_debug", "Encountered an error parsing user input in investment.");
    		// let the user know the input is wrong
    		investOutputError.setText("Warning - Input error, make sure you entered a " +
    				"valid value in each field above.");
        	//reset the output dialog as well
        	outputFinalValue.setText("");
    		return; //get outta here johnny!
    	}
    	
    	//calculate final value 
    	finalValue = calculateFinalValue(principle_num, interestPercent, term_num);
  
    	//convert results to decimal format and output
    	outputFinalValue.setText(df.format(finalValue)); 
    }

    /****************************************************************
		FUNCTION:   double calculateFinalValue(double, double, int)
		ARGUMENTS:  principle (double), annual interest rate (double), term in years (int)
		RETURNS:    final value (double)
		NOTES:      This calculates the final value for an investment.
     ****************************************************************/
    public double calculateFinalValue(double principle, double annualInterestRate, int numYears)
    	{
    		return principle * Math.pow((1 + annualInterestRate), numYears);
    	}

    /****************************************************************
		FUNCTION:   void clearInvestInputs(View)
		ARGUMENTS:  button (View)
		RETURNS:    nothing
		NOTES:      This clears the input and output fields for the investment tab.
     ****************************************************************/
    public void clearInvestInputs(View theButton) {
    	//wipe it all clean
    	investPrinciple.setText("");
    	investInterest.setText("");
    	investTerm.setText("");
    	
    	//reset all the output dialog as well
    	outputFinalValue.setText("");
    	
        investOutputError.setText(""); //reset the error messages


    	Log.d("A2_debug", "Cleared all the investment inputs.");
    }
}