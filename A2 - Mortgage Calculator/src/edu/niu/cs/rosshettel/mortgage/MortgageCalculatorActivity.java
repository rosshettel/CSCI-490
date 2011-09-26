package edu.niu.cs.rosshettel.mortgage;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MortgageCalculatorActivity extends Activity {
	EditText principle, interest, term;
	Button calculate, clear;
	TextView output_mortgage, output_repayment, output_error;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        principle = (EditText) findViewById(R.id.mortgage_input);
        interest = (EditText) findViewById(R.id.interest_input);
        term = (EditText) findViewById(R.id.term_input);
        
        calculate = (Button) findViewById(R.id.calculate_button);
        
        output_mortgage = (TextView) findViewById(R.id.output_mortgage);
        output_repayment = (TextView) findViewById(R.id.output_repayment);
        output_error = (TextView) findViewById(R.id.output_error);
    }
    
    /****************************************************************
		FUNCTION:   void calculateMortgage(View theButton)
		ARGUMENTS:  view
		RETURNS:    none
		NOTES:      Get all the user inputs, convert to respective data types,
					then calculate the mortgage and output.
     ****************************************************************/
    public void calculateMortgage(View theButton) {
    	double principle_num = 0;
    	double interest_num = 0;
    	double monthlyResult, repaymentResult, interestPercent;
    	int term_num = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        
        output_error.setText(""); //reset the error messages
        
        //try to convert the user input to doubles/int, error if exception found
    	try {
    		principle_num = Double.parseDouble(principle.getText().toString());
    		interest_num = Double.parseDouble(interest.getText().toString());
    		interestPercent = interest_num / 100; //turn the whole into a decimal percentage
    		term_num = Integer.parseInt(term.getText().toString());
    	} catch (final NumberFormatException e) {
    		Log.d("A2_debug", "We encountered an error parsing user input.");
    		// let the user know the input is wrong
    		clearInputs((Button) findViewById(R.id.clear_button)); //clear all outputs first
    		output_error.setText("Warning - There was a problem parsing user input, please try again.");
    		return; //get outta here johnny!
    	}
    	
    	//calculate payments 
    	monthlyResult = calculateMonthlyPayment(principle_num, interestPercent, term_num);
    	repaymentResult = calculateTotalRepayment(principle_num, interestPercent, term_num);
  
    	//convert results to decimal format and output
    	output_mortgage.setText("Your monthly payment is: $" + df.format(monthlyResult)); 
    	output_repayment.setText("Your total repayment is: $" + df.format(repaymentResult));
    }
    
    /*
     * Grab the data from the user inputs, and calculate the mortgage rate
     * John Miller makes an edit here to test github.
     */
    
    /****************************************************************
    	FUNCTION:   double calculateMonthlyPayment(double, double, int)
    	ARGUMENTS:  principle (double), annual interest rate (double), term in months (int)
    	RETURNS:    monthly payment (double)
    	NOTES:      This calculates the monthly payment for a mortgage.
     ****************************************************************/
    public double calculateMonthlyPayment(double principle, double annualInterestRate, int numMonths) 
    	{
   			double monthlyInterestRate = annualInterestRate/12;
   			Log.d("A2_debug", "monthlyInterestRate: " + monthlyInterestRate);
    		double monthlyPmt = principle*monthlyInterestRate/(1-Math.pow((1+monthlyInterestRate),(-numMonths)));
    		return monthlyPmt;
   		}
    /****************************************************************
    	FUNCTION:   double calculateTotalRepayment(double, double, int)
    	ARGUMENTS:  principle (double), annual interest rate (double), term in months (int)
    	RETURNS:    total amount of repayment (double)
    	NOTES:      This calculates the total repayment on a mortgage.
     ****************************************************************/
    public double calculateTotalRepayment(double principle, double annualInterestRate, int numMonths)
    	{
    		double finalValue = principle*Math.pow((1+annualInterestRate), (numMonths/12));
    		return finalValue;
    	}
    /*
     * Clear all the input data
     */
    public void clearInputs(View theButton) {
    	//wipe it all clean
    	principle.setText("");
    	interest.setText("");
    	term.setText("");
    	
    	//reset all the output dialog as well
    	output_mortgage.setText("");
		output_repayment.setText("");
		
    	Log.d("A2_debug", "Cleared all the inputs.");
    }
}
