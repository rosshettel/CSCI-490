/****************************************************************
   FILE:      MortgageActivity.java
   AUTHOR:    Ross Hettel, John Miller, Alex Wohead
   LOGON ID:  Z1549355, Z159807, Z1624450
   DUE DATE:  10/17 at class time

   PURPOSE:   This will set up the Mortgage tab as well as process any
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

public class MortgageActivity extends Activity
{
    //widgets for the mortgage calculator	
    EditText mortPrinciple, mortInterest, mortTerm;
    Button mortCalculate, clear;
    TextView output_mortgage, output_repayment, mortOutputError;
    
    /****************************************************************
		FUNCTION:   void onCreate(Bundle)
		ARGUMENTS:  saved instance state (Bundle)
		RETURNS:    nothing
		NOTES:      This is equivalent to main() in other programming languages.
		            This method sets the widgets to their id's
		            Then it will setOnClickListeners on the 
		            calculate button and the clear button.
     ****************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);	
	
	//edit texts for input
	mortPrinciple = (EditText) findViewById(R.id.amount_borrow_input);
        mortInterest = (EditText) findViewById(R.id.rate_borrow_input);
        mortTerm = (EditText) findViewById(R.id.time_borrow_input);
        
        //calculate and clear button for the calculations and clearing inputs
        mortCalculate = (Button) findViewById(R.id.calculate_button_borrow);
        clear = (Button) findViewById(R.id.clear_button_borrow);
        
        //output fields for the totals and errors
        output_mortgage = (TextView) findViewById(R.id.monthly_payment_output);
        output_repayment = (TextView) findViewById(R.id.total_payment_output);
        mortOutputError = (TextView) findViewById(R.id.borrow_error);
        
        //setting an onClickListener for the calculate button
        mortCalculate.setOnClickListener(new View.OnClickListener()
        {
        		public void onClick(View v)
        		{
        				calculateMortgage(v);
        		}
        });
        //setting an onClickListener for the clear button
        clear.setOnClickListener(new View.OnClickListener()
        {
        		public void onClick(View v)
        		{
        				clearMortInputs(v);
        		}
        });
        
        
	}
	/****************************************************************
		FUNCTION:   void calculateMortgage(View theButton)
		ARGUMENTS:  view
		RETURNS:    none
		NOTES:      Get all the user inputs, convert to respective data types,
					then calculate the mortgage and output.
     ****************************************************************/
    public void calculateMortgage(View theButton) {
    	Log.d("A2_debug", "Got to calculate mortgage function");
    	double principle_num = 0;
    	double interest_num = 0;
    	double monthlyResult, repaymentResult, interestPercent;
    	int term_num = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        mortOutputError.setText(""); //reset the error messages
        
        //try to convert the user input to doubles/int, error if exception found
    	try {
    		principle_num = Double.parseDouble(mortPrinciple.getText().toString());
    		interest_num = Double.parseDouble(mortInterest.getText().toString());
    		interestPercent = interest_num / 100; //turn the whole into a decimal percentage
    		term_num = Integer.parseInt(mortTerm.getText().toString());
    	} catch (final NumberFormatException e) {
    		Log.d("A2_debug", "Encountered an error parsing user input in mortgage.");
    		// let the user know the input is wrong
    		mortOutputError.setText("Warning - Input error, make sure you entered a valid value in each field above.");
        	//reset the output dialog as well
        	output_mortgage.setText("");
    		output_repayment.setText("");
    		return; //get outta here johnny!
    	}
    	
    	//calculate payments 
    	monthlyResult = calculateMonthlyPayment(principle_num, interestPercent, term_num*12);
    	repaymentResult = calculateTotalRepayment(monthlyResult, term_num*12);
  
    	//convert results to decimal format and output
    	output_mortgage.setText(df.format(monthlyResult)); 
    	output_repayment.setText(df.format(repaymentResult));
    }

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
		FUNCTION:   double calculateTotalRepayment(double, int)
		ARGUMENTS:  monthly payment (double), term in months (int)
		RETURNS:    total amount of repayment (double)
		NOTES:      This calculates the total repayment on a mortgage.
     ****************************************************************/
    public double calculateTotalRepayment(double monthlyPayment, int numMonths)
    	{
    		return monthlyPayment*numMonths;
    	}
    
    /****************************************************************
		FUNCTION:   void clearMortInputs(View)
		ARGUMENTS:  button (View)
		RETURNS:    nothing
		NOTES:      This clears the input and output fields for the mortgage tab.
     ****************************************************************/
    public void clearMortInputs(View theButton) {
    	//wipe it all clean
    	mortPrinciple.setText("");
    	mortInterest.setText("");
    	mortTerm.setText("");
    	
    	//reset all the output dialog as well
    	output_mortgage.setText("");
		output_repayment.setText("");

        mortOutputError.setText(""); //reset the error messages

    	Log.d("A2_debug", "Cleared all the mortgage inputs.");
    }

}