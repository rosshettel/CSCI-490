package edu.niu.cs.rosshettel.Assignment3;

import java.text.DecimalFormat;

import android.util.Log;
import android.view.View;

public class Investment extends A3TabbedCalculatorActivity
{
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
    		investOutputError.setText("Warning - Input error, make sure you entered a valid value in each field above.");
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